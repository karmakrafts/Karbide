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
import kotlinx.io.readUInt
import kotlinx.io.readUIntLe

internal data class BitSource32( // @formatter:off
    private val source: Source,
    private val isSourceOwned: Boolean,
    override val bitOrder: BitOrder
) : BitSource { // @formatter:on
    private val isMsbFirst: Boolean = bitOrder == BitOrder.MSB_FIRST

    private var isClosed: Boolean = false

    override val byte: Long
        get() = ((fetchedByteHigh.toLong() shl Int.SIZE_BITS) or fetchedByteLow.toLong()) -
                ((bitInBuffer + 7) shr 3)
    override val bit: Int get() = (-bitInBuffer) and 7

    override val exhausted: Boolean
        get() = bitInBuffer == 0 && source.exhausted()

    // Keep the fetched-byte position split into two words so buffer refills do not
    // require Long arithmetic on 32-bit targets. It is joined only when the public
    // Long byte position is queried.
    private var fetchedByteLow: UInt = 0U
    private var fetchedByteHigh: UInt = 0U
    private var bitInBuffer: Int = 0
    private var buffer: UInt = 0U
    private val scratch: ByteArray = ByteArray(UInt.SIZE_BYTES)

    @Suppress("NOTHING_TO_INLINE")
    private inline fun checkReadCount(count: Int) {
        require(count in 0..ULong.SIZE_BITS) { "count must be between 0 and ${ULong.SIZE_BITS}" }
    }

    // The buffer is top-aligned: the next bit to be consumed is at bit 31 and all
    // bits below the buffered range are always zero. This allows extracting chunks
    // with a single shift and without any per-read bit reversal. The double shift
    // keeps this branch-free for the full 1..32 count range.
    @Suppress("NOTHING_TO_INLINE")
    private inline fun consumeBufferedBits(count: Int): UInt {
        val result = buffer shr (UInt.SIZE_BITS - count)
        buffer = (buffer shl (count - 1)) shl 1
        bitInBuffer -= count
        return result
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun discardBufferedBits(count: Int) {
        buffer = (buffer shl (count - 1)) shl 1
        bitInBuffer -= count
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun addFetchedBytes(count: Int) {
        val previous = fetchedByteLow
        fetchedByteLow += count.toUInt()
        if (fetchedByteLow < previous) fetchedByteHigh++
    }

    private fun fillBuffer() {
        var bits = bitInBuffer
        var freeBytes = (UInt.SIZE_BITS - bits) shr 3
        if (freeBytes == 0) return
        if (bits == 0 && source.request(UInt.SIZE_BYTES.toLong())) {
            buffer = if (isMsbFirst) source.readUInt() else source.readUIntLe().reverseBits()
            bitInBuffer = UInt.SIZE_BITS
            addFetchedBytes(UInt.SIZE_BYTES)
            return
        }
        // Bulk-read up to freeBytes bytes in a single call and assemble them into a
        // little-endian word, which is then brought into stream order with a single
        // reverseBytes (MSB first) or reverseBits (LSB first) intrinsic.
        while (freeBytes > 0) {
            val read = source.readAtMostTo(scratch, 0, freeBytes)
            if (read <= 0) break
            var word = 0U
            for (index in 0 until read) {
                word = word or ((scratch[index].toUInt() and 0xFFU) shl (index shl 3))
            }
            word = if (isMsbFirst) word.reverseBytes() else word.reverseBits()
            buffer = buffer or (word shr bits)
            bits += read shl 3
            freeBytes -= read
            addFetchedBytes(read)
        }
        bitInBuffer = bits
    }

    private fun readAcrossBuffer(count: Int): UInt {
        val bufferedCount = bitInBuffer
        val missingCount = count - bufferedCount
        val result = consumeBufferedBits(bufferedCount) shl missingCount
        fillBuffer()
        return result or consumeBufferedBits(missingCount)
    }

    private fun readAvailableBits32(count: Int): UInt {
        if (count <= bitInBuffer) return consumeBufferedBits(count)
        fillBuffer()
        if (count <= bitInBuffer) return consumeBufferedBits(count)
        return readAcrossBuffer(count)
    }

    internal fun readBits32(count: Int): UInt {
        if (count in 1..bitInBuffer) return consumeBufferedBits(count)
        require(count in 0..UInt.SIZE_BITS) { "count must be between 0 and ${UInt.SIZE_BITS}" }
        if (count == 0) return 0U
        fillBuffer()
        if (count <= bitInBuffer) return consumeBufferedBits(count)
        requireBits(count)
        return readAcrossBuffer(count)
    }

    internal fun peekBits32(count: Int): UInt {
        require(count in 0..UInt.SIZE_BITS) { "count must be between 0 and ${UInt.SIZE_BITS}" }
        if (count == 0) return 0U
        if (count <= bitInBuffer) return buffer shr (UInt.SIZE_BITS - count)
        fillBuffer()
        if (count <= bitInBuffer) return buffer shr (UInt.SIZE_BITS - count)
        requireBits(count)
        val bufferedCount = bitInBuffer
        val bufferedBits = buffer shr (UInt.SIZE_BITS - bufferedCount)
        val missing = count - bufferedCount
        val remainingBits = BitSource32(source.peek(), true, bitOrder).use { it.readBits32(missing) }
        if (bufferedCount == 0) {
            return remainingBits
        }
        return (bufferedBits shl missing) or remainingBits
    }

    override fun requestBits(count: Int): Boolean {
        require(count >= 0) { "count must not be negative" }
        if (count <= bitInBuffer) return true
        val missingBits = count - bitInBuffer
        val missingBytes = (missingBits shr 3) + if (missingBits and 7 == 0) 0 else 1
        return source.request(missingBytes.toLong())
    }

    override fun requireBits(count: Int) {
        if (requestBits(count)) return
        throw EOFException("Source exhausted before $count bits could be read")
    }

    override fun peekBits(count: Int): ULong {
        checkReadCount(count)
        if (count == 0) return 0UL
        if (count <= bitInBuffer) return (buffer shr (UInt.SIZE_BITS - count)).toULong()
        fillBuffer()
        if (count <= bitInBuffer) return (buffer shr (UInt.SIZE_BITS - count)).toULong()
        requireBits(count)
        val bufferedCount = bitInBuffer
        val bufferedBits = buffer shr (UInt.SIZE_BITS - bufferedCount)
        val missing = count - bufferedCount
        val remainingBits = BitSource32(source.peek(), true, bitOrder).use { it.readBits(missing) }
        if (bufferedCount == 0) {
            return remainingBits
        }
        return (bufferedBits.toULong() shl missing) or remainingBits
    }

    override fun readBits(count: Int): ULong {
        if (count <= UInt.SIZE_BITS) return readBits32(count).toULong()
        checkReadCount(count)
        fillBuffer()
        requireBits(count)
        val highBits = readAvailableBits32(count - UInt.SIZE_BITS)
        val lowBits = readAvailableBits32(UInt.SIZE_BITS)
        return (highBits.toULong() shl UInt.SIZE_BITS) or lowBits.toULong()
    }

    override fun skipBits(count: Int) {
        require(count >= 0) { "Bit count must not be negative" }
        if (count == 0) return
        if (count <= bitInBuffer) {
            discardBufferedBits(count)
            return
        }
        fillBuffer()
        if (count <= bitInBuffer) {
            discardBufferedBits(count)
            return
        }
        requireBits(count)
        var remaining = count
        if (bitInBuffer > 0) {
            remaining -= bitInBuffer
            discardBufferedBits(bitInBuffer)
        }
        val bytes = remaining shr 3
        if (bytes > 0) {
            source.skip(bytes.toLong())
            addFetchedBytes(bytes)
            remaining -= bytes shl 3
        }
        if (remaining > 0) {
            fillBuffer()
            discardBufferedBits(remaining)
        }
    }

    override fun skipUntilNextByte() {
        val count = (Byte.SIZE_BITS - bit) and 7
        if (count > 0) skipBits(count)
    }

    override fun reset() {
        if (isClosed) return
        buffer = 0U
        bitInBuffer = 0
        fetchedByteLow = 0U
        fetchedByteHigh = 0U
    }

    override fun close() {
        if (isClosed) return
        if (isSourceOwned) source.close()
        reset()
        isClosed = true
    }
}