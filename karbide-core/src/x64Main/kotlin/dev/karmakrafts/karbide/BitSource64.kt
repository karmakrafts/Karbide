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

internal data class BitSource64( // @formatter:off
    private val source: Source,
    private val isSourceOwned: Boolean,
    override val bitOrder: BitOrder
) : BitSource { // @formatter:on
    private val isMsbFirst: Boolean = bitOrder == BitOrder.MSB_FIRST

    private var isClosed: Boolean = false

    override val byte: Long get() = bitsRead shr 3
    override val bit: Int get() = (bitsRead and 7L).toInt()

    override val exhausted: Boolean
        get() = bitInBuffer == 0 && source.exhausted()

    // The number of bits consumed so far is derived from the number of bits fetched
    // from the source, so the hot read paths only need to update the buffer and its
    // fill level instead of maintaining a separate read counter.
    private val bitsRead: Long get() = bitsFetched - bitInBuffer

    private var bitsFetched: Long = 0L
    private var bitInBuffer: Int = 0
    private var buffer: Long = 0L
    private val scratch: ByteArray = ByteArray(Long.SIZE_BYTES)

    @Suppress("NOTHING_TO_INLINE")
    private inline fun checkReadCount(count: Int) {
        require(count in 0..ULong.SIZE_BITS) { "count must be between 0 and ${ULong.SIZE_BITS}" }
    }

    // The buffer is top-aligned: the next bit to be consumed is at bit 63 and all
    // bits below the buffered range are always zero. This allows extracting chunks
    // with a single shift and without any per-read bit reversal. A full-word read
    // is handled explicitly because JVM shifts mask the distance to six bits.
    @Suppress("NOTHING_TO_INLINE")
    private inline fun consumeBufferedBits(count: Int): Long {
        val result = buffer ushr (Long.SIZE_BITS - count)
        buffer = if (count == Long.SIZE_BITS) 0L else buffer shl count
        bitInBuffer -= count
        return result
    }

    private fun fillBuffer() {
        var bits = bitInBuffer
        var freeBytes = (ULong.SIZE_BITS - bits) shr 3
        if (freeBytes == 0) return
        if (bits == 0 && source.request(ULong.SIZE_BYTES.toLong())) {
            buffer = if (isMsbFirst) source.readLong() else source.readLongLeFast().reverseBits()
            bitInBuffer = ULong.SIZE_BITS
            bitsFetched += ULong.SIZE_BITS
            return
        }
        // Bulk-read up to freeBytes bytes in a single call and assemble them into a
        // little-endian word, which is then brought into stream order with a single
        // reverseBytes (MSB first) or reverseBits (LSB first) intrinsic.
        while (freeBytes > 0) {
            val read = source.readAtMostTo(scratch, 0, freeBytes)
            if (read <= 0) break
            var word = 0L
            for (index in 0 until read) {
                word = word or ((scratch[index].toLong() and 0xFFL) shl (index shl 3))
            }
            word = if (isMsbFirst) word.reverseBytes() else word.reverseBits()
            buffer = buffer or (word ushr bits)
            bits += read shl 3
            freeBytes -= read
        }
        bitsFetched += bits - bitInBuffer
        bitInBuffer = bits
    }

    private fun readAcrossBuffer(count: Int): Long {
        val bufferedCount = bitInBuffer
        val missingCount = count - bufferedCount
        val result = consumeBufferedBits(bufferedCount) shl missingCount
        fillBuffer()
        return result or consumeBufferedBits(missingCount)
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
        if (count <= bitInBuffer) return (buffer ushr (Long.SIZE_BITS - count)).toULong()
        fillBuffer()
        if (count <= bitInBuffer) return (buffer ushr (Long.SIZE_BITS - count)).toULong()
        requireBits(count)
        val bufferedCount = bitInBuffer
        val missingCount = count - bufferedCount
        val remainingBits = BitSource64(source.peek(), true, bitOrder).use { it.readBits(missingCount).toLong() }
        if (bufferedCount == 0) return remainingBits.toULong()
        val bufferedBits = buffer ushr (Long.SIZE_BITS - bufferedCount)
        return ((bufferedBits shl missingCount) or remainingBits).toULong()
    }

    override fun readBits(count: Int): ULong {
        if (count in 1..bitInBuffer) return consumeBufferedBits(count).toULong()
        checkReadCount(count)
        if (count == 0) return 0UL
        fillBuffer()
        if (count <= bitInBuffer) return consumeBufferedBits(count).toULong()
        requireBits(count)
        return readAcrossBuffer(count).toULong()
    }

    override fun skipBits(count: Int) {
        require(count >= 0) { "Bit count must not be negative" }
        if (count == 0) return
        if (count <= bitInBuffer) {
            buffer = if (count == Long.SIZE_BITS) 0L else buffer shl count
            bitInBuffer -= count
            return
        }
        fillBuffer()
        if (count <= bitInBuffer) {
            buffer = if (count == Long.SIZE_BITS) 0L else buffer shl count
            bitInBuffer -= count
            return
        }
        requireBits(count)
        var remaining = count
        while (remaining > 0) {
            fillBuffer()
            val take = if (remaining <= bitInBuffer) remaining else bitInBuffer
            buffer = if (take == Long.SIZE_BITS) 0L else buffer shl take
            bitInBuffer -= take
            remaining -= take
        }
    }

    override fun skipUntilNextByte() {
        val count = (Byte.SIZE_BITS - bit) and 7
        if (count > 0) skipBits(count)
    }

    override fun reset() {
        if (isClosed) return
        buffer = 0L
        bitInBuffer = 0
        bitsFetched = 0L
    }

    override fun close() {
        if (isClosed) return
        if (isSourceOwned) source.close()
        reset()
        isClosed = true
    }
}