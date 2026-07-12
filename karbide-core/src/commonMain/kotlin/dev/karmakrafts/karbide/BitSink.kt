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

import kotlinx.io.Sink
import kotlinx.io.writeUByte
import kotlinx.io.writeULong
import kotlinx.io.writeULongLe

/**
 * Interface for writing individual bits to a sink.
 */
interface BitSink : AutoCloseable {
    /**
     * The bit order of this bit sink.
     */
    val bitOrder: BitOrder

    /**
     * The current byte offset in the sink.
     */
    val byte: Long

    /**
     * The current bit offset within the current byte (0-7).
     */
    val bit: Int

    /**
     * Write the specified number of bits to the sink.
     *
     * @param count The number of bits to write.
     * @param bits The bits to write as a [ULong].
     */
    fun writeBits(count: Int, bits: ULong)

    /**
     * Write the specified number of padding bits to the sink.
     *
     * @param count The number of bits to write.
     * @param value The value of the bits to write (0 or 1).
     */
    fun padBits(count: Int, value: UByte)

    /**
     * Write the specified number of padding bits to the sink.
     *
     * @param value The value of the bits to write (0 or 1).
     */
    fun padToNextByte(value: UByte)

    /**
     * Flush any remaining data buffered internally into the
     * underlying [Sink].
     */
    fun flush()

    /**
     * Reset the internal state of this sink.
     * This will reset [bit] and [byte] counters to 0.
     */
    fun reset()
}

private data class BitSinkImpl( // @formatter:off
    private val sink: Sink,
    private val isSinkOwned: Boolean,
    override val bitOrder: BitOrder
) : BitSink { // @formatter:on
    private val isMsbFirst: Boolean = bitOrder == BitOrder.MSB_FIRST

    private var isClosed: Boolean = false

    override val byte: Long get() = (bitsEmitted + bitInBuffer) shr 3
    override val bit: Int get() = bitInBuffer and 7 // % 8

    // The number of bits already pushed to the underlying sink. Together with the
    // buffer fill level this yields the logical write position, so the hot write
    // path only needs to update the buffer and its fill level.
    private var bitsEmitted: Long = 0L
    private var bitInBuffer: Int = 0
    private var buffer: ULong = 0UL

    // The buffer is top-aligned: the first bit written ends up at bit 63 and bits
    // below the buffered range are always zero. Since writeBits emits the most
    // significant of the [count] bits first, chunks enter the buffer with plain
    // shifts and without any per-write bit reversal. A full buffer is written out
    // as a single 64-bit word which is either already in MSB-first stream order or
    // brought into LSB-first order with a single reverseBits intrinsic.
    private fun emitWord(word: ULong) {
        if (isMsbFirst) sink.writeULong(word) else sink.writeULongLe(word.reverseBits())
        bitsEmitted += ULong.SIZE_BITS
    }

    /**
     * Drains all buffered bits to the underlying sink, including a
     * trailing zero-padded partial byte if present.
     */
    private fun drainBytes() {
        var remaining = bitInBuffer
        var localBuffer = buffer
        while (remaining > 0) {
            val value = (localBuffer shr (ULong.SIZE_BITS - Byte.SIZE_BITS)).toUByte()
            sink.writeUByte(if (isMsbFirst) value else value.reverseBits())
            localBuffer = localBuffer shl Byte.SIZE_BITS
            bitsEmitted += Byte.SIZE_BITS
            remaining -= Byte.SIZE_BITS
        }
        buffer = 0UL
        bitInBuffer = 0
    }

    override fun writeBits(count: Int, bits: ULong) {
        if (count == 0) return
        val offset = bitInBuffer
        // Top-align the chunk; bits above [count] are shifted out implicitly
        val chunk = bits shl (ULong.SIZE_BITS - count)
        buffer = buffer or (chunk shr offset)
        val total = offset + count
        if (total < ULong.SIZE_BITS) {
            bitInBuffer = total
            return
        }
        emitWord(buffer)
        // Carry over the bits that did not fit; the double shift keeps this
        // branch-free for the full 1..64 consumed-bit range
        val consumed = ULong.SIZE_BITS - offset
        buffer = (chunk shl (consumed - 1)) shl 1
        bitInBuffer = total - ULong.SIZE_BITS
    }

    override fun padBits(count: Int, value: UByte) {
        val bitValue = value.toULong() and 0b1UL
        val bits = when (bitValue) { // @formatter:off
            1UL -> when (count) {
                ULong.SIZE_BITS -> ULong.MAX_VALUE
                else -> (1UL shl count) - 1UL
            }
            else -> 0UL
        } // @formatter:on
        writeBits(count, bits)
    }

    override fun padToNextByte(value: UByte) {
        val count = (Byte.SIZE_BITS - bit) and 7
        if (count > 0) padBits(count, value)
    }

    override fun flush() {
        if (isClosed) return
        val isAligned = bit == 0
        drainBytes()
        if (!isAligned) reset()
    }

    override fun reset() {
        if (isClosed) return
        buffer = 0UL
        bitInBuffer = 0
        bitsEmitted = 0L
    }

    override fun close() {
        if (isClosed) return
        flush()
        if (isSinkOwned) sink.close()
        isClosed = true
    }
}

/**
 * Create a new [BitSink] from the current [Sink].
 *
 * @param isSinkOwned Whether the [Sink] is owned by the [BitSink] and should be closed when it is.
 * @param bitOrder Whether bits are written out LSB or MSB first.
 * @return A new [BitSink] instance.
 */
fun Sink.bitSink(
    isSinkOwned: Boolean = true, bitOrder: BitOrder = BitOrder.MSB_FIRST
): BitSink = BitSinkImpl(this, isSinkOwned, bitOrder)