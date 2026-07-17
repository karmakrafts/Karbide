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
import kotlinx.io.writeUInt
import kotlinx.io.writeUIntLe

internal data class BitSink32( // @formatter:off
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
    private var buffer: UInt = 0U

    // The buffer is top-aligned: the first bit written ends up at bit 31 and bits
    // below the buffered range are always zero. Since writeBits emits the most
    // significant of the [count] bits first, chunks enter the buffer with plain
    // shifts and without any per-write bit reversal. A full buffer is written out
    // as a single 32-bit word which is either already in MSB-first stream order or
    // brought into LSB-first order with a single reverseBits intrinsic.
    private fun emitWord(word: UInt) {
        if (isMsbFirst) sink.writeUInt(word) else sink.writeUIntLe(word.reverseBits())
        bitsEmitted += UInt.SIZE_BITS
    }

    /**
     * Drains all buffered bits to the underlying sink, including a
     * trailing zero-padded partial byte if present.
     */
    private fun drainBytes() {
        var remaining = bitInBuffer
        var localBuffer = buffer
        while (remaining > 0) {
            val value = (localBuffer shr (UInt.SIZE_BITS - Byte.SIZE_BITS)).toUByte()
            sink.writeUByte(if (isMsbFirst) value else value.reverseBits())
            localBuffer = localBuffer shl Byte.SIZE_BITS
            bitsEmitted += Byte.SIZE_BITS
            remaining -= Byte.SIZE_BITS
        }
        buffer = 0U
        bitInBuffer = 0
    }

    private fun writeChunk(count: Int, bits: UInt) {
        val offset = bitInBuffer
        // Top-align the chunk; bits above [count] are shifted out implicitly
        val chunk = bits shl (UInt.SIZE_BITS - count)
        buffer = buffer or (chunk shr offset)
        val total = offset + count
        if (total < UInt.SIZE_BITS) {
            bitInBuffer = total
            return
        }
        emitWord(buffer)
        // Carry over the bits that did not fit; the double shift handles a
        // consumed-bit count of 32 despite Kotlin masking shift distances.
        val consumed = UInt.SIZE_BITS - offset
        buffer = (chunk shl (consumed - 1)) shl 1
        bitInBuffer = total - UInt.SIZE_BITS
    }

    internal fun writeBits32(count: Int, bits: UInt) {
        require(count in 0..UInt.SIZE_BITS) { "count must be between 0 and ${UInt.SIZE_BITS}" }
        if (count == 0) return
        writeChunk(count, bits)
    }

    override fun writeBits(count: Int, bits: ULong) {
        if (count <= UInt.SIZE_BITS) {
            writeBits32(count, bits.toUInt())
            return
        }
        val remaining = count - UInt.SIZE_BITS
        writeChunk(UInt.SIZE_BITS, (bits shr remaining).toUInt())
        writeChunk(remaining, bits.toUInt())
    }

    override fun padBits(count: Int, value: UByte) {
        val bits = if (value.toUInt() and 0b1U == 1U) UInt.MAX_VALUE else 0U
        var remaining = count
        while (remaining > UInt.SIZE_BITS) {
            writeChunk(UInt.SIZE_BITS, bits)
            remaining -= UInt.SIZE_BITS
        }
        if (remaining > 0) writeChunk(remaining, bits)
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
        buffer = 0U
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