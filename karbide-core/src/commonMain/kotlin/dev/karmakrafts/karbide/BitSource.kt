/*
 * Copyright 2026 Karma Krafts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.karmakrafts.karbide

import kotlinx.io.EOFException
import kotlinx.io.Source
import kotlinx.io.readUByte
import kotlin.math.min

/**
 * Interface for reading individual bits from a source.
 */
interface BitSource : AutoCloseable {
    /**
     * The bit order of this bit source.
     */
    val bitOrder: BitOrder

    /**
     * The current byte offset in the source.
     */
    val byte: Long

    /**
     * The current bit offset within the current byte (0-7).
     */
    val bit: Int

    /**
     * Whether the source has been exhausted and all bits have been read.
     *
     * This is only true when the internal bit buffer is empty and the underlying source is exhausted. Use
     * [requestBits] or [requireBits] to check whether a specific number of bits can be read.
     */
    val exhausted: Boolean

    /**
     * Request that at least [count] bits are available without consuming them.
     *
     * @param count The number of bits that should be available.
     * @return `true` if at least [count] bits can be read, `false` otherwise.
     */
    fun requestBits(count: Int): Boolean

    /**
     * Require that at least [count] bits are available without consuming them.
     *
     * @param count The number of bits that should be available.
     * @throws EOFException If fewer than [count] bits are available.
     */
    fun requireBits(count: Int)

    /**
     * Read the specified number of bits without consuming them.
     *
     * @param count The number of bits to peek.
     * @return The bits read as a [ULong].
     */
    fun peekBits(count: Int): ULong

    /**
     * Read the specified number of bits from the source.
     *
     * @param count The number of bits to read.
     * @return The bits read as a [ULong].
     */
    fun readBits(count: Int): ULong

    /**
     * Skip the specified number of bits in the source.
     *
     * @param count The number of bits to skip.
     */
    fun skipBits(count: Int)

    /**
     * Align the reader to the next byte boundary.
     */
    fun skipUntilNextByte()

    /**
     * Reset the internal state of this source.
     * This will reset [bit] and [byte] counters to 0.
     */
    fun reset()
}

private data class BitSourceImpl( // @formatter:off
    private val source: Source,
    private val isSourceOwned: Boolean,
    override val bitOrder: BitOrder
) : BitSource { // @formatter:on
    private var isClosed: Boolean = false

    override val byte: Long get() = bitsRead shr 3
    override val bit: Int get() = (bitsRead and 7).toInt()

    override val exhausted: Boolean
        get() = bitInBuffer == 0 && source.exhausted()

    private var bitsRead: Long = 0L
    private var bitInBuffer: Int = 0
    private var buffer: ULong = 0UL

    private fun checkReadCount(count: Int) {
        require(count in 0..ULong.SIZE_BITS) { "count must be between 0 and ${ULong.SIZE_BITS}" }
    }

    private fun fillBuffer() {
        while (bitInBuffer <= ULong.SIZE_BITS - Byte.SIZE_BITS && !source.exhausted()) {
            var byteValue = source.readUByte()
            if (bitOrder == BitOrder.MSB_FIRST) {
                byteValue = byteValue.reverseBits()
            }
            buffer = buffer or (byteValue.toULong() shl bitInBuffer)
            bitInBuffer += Byte.SIZE_BITS
        }
    }

    private fun readBufferedBits(count: Int): ULong {
        var remaining = count
        var result = 0UL
        while (remaining > 0) {
            fillBuffer()
            val take = min(remaining, bitInBuffer)
            val chunk = if (take == ULong.SIZE_BITS) buffer
            else {
                val mask = (1UL shl take) - 1UL
                buffer and mask
            }
            val reversedChunk = chunk.reverseBits(take)
            result = result or (reversedChunk shl (remaining - take))
            buffer = if (take == ULong.SIZE_BITS) 0UL else buffer shr take
            bitInBuffer -= take
            remaining -= take
            bitsRead += take
        }
        return result
    }

    override fun requestBits(count: Int): Boolean {
        require(count >= 0) { "count must not be negative" }
        if (count <= bitInBuffer) return true
        val missingBits = count.toLong() - bitInBuffer
        val missingBytes = (missingBits + Byte.SIZE_BITS - 1) / Byte.SIZE_BITS
        return source.request(missingBytes)
    }

    override fun requireBits(count: Int) {
        if (requestBits(count)) return
        throw EOFException("Source exhausted before $count bits could be read")
    }

    override fun peekBits(count: Int): ULong {
        checkReadCount(count)
        if (count == 0) return 0UL
        requireBits(count)
        fillBuffer()
        check(count <= bitInBuffer) { "Cannot peek $count bits with the current bit alignment" }
        var remaining = count
        var bufferedBits = bitInBuffer
        var bufferedValue = buffer
        var result = 0UL
        while (remaining > 0) {
            val take = min(remaining, bufferedBits)
            val chunk = if (take == ULong.SIZE_BITS) bufferedValue
            else {
                val mask = (1UL shl take) - 1UL
                bufferedValue and mask
            }
            val reversedChunk = chunk.reverseBits(take)
            result = result or (reversedChunk shl (remaining - take))
            bufferedValue = if (take == ULong.SIZE_BITS) 0UL else bufferedValue shr take
            bufferedBits -= take
            remaining -= take
        }
        return result
    }

    override fun readBits(count: Int): ULong {
        checkReadCount(count)
        if (count == 0) return 0UL
        requireBits(count)
        return readBufferedBits(count)
    }

    override fun skipBits(count: Int) {
        require(count >= 0) { "Bit count must not be negative" }
        requireBits(count)
        var remaining = count
        while (remaining > 0) {
            fillBuffer()
            val take = min(remaining, bitInBuffer)
            buffer = if (take == ULong.SIZE_BITS) 0UL else buffer shr take
            bitInBuffer -= take
            remaining -= take
            bitsRead += take
        }
    }

    override fun skipUntilNextByte() {
        val count = (Byte.SIZE_BITS - bit) and 7
        if (count > 0) skipBits(count)
    }

    override fun reset() {
        if (isClosed) return
        buffer = 0UL
        bitInBuffer = 0
        bitsRead = 0L
    }

    override fun close() {
        if (isClosed) return
        if (isSourceOwned) source.close()
        reset()
        isClosed = true
    }
}

/**
 * Create a new [BitSource] from the current [Source].
 *
 * @param isSourceOwned Whether the [Source] is owned by the [BitSource] and should be closed when it is.
 * @param bitOrder Whether bits are read in LSB or MSB first.
 * @return A new [BitSource] instance.
 */
fun Source.bitSource( // @formatter:off
    isSourceOwned: Boolean = true,
    bitOrder: BitOrder = BitOrder.MSB_FIRST
): BitSource = BitSourceImpl(this, isSourceOwned, bitOrder) // @formatter:on