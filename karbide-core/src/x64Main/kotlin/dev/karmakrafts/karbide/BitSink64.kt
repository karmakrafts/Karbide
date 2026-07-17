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

internal data class BitSink64( // @formatter:off
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
    private var buffer: Long = 0L

    // The buffer is top-aligned: the first bit written ends up at bit 63 and bits
    // below the buffered range are always zero. Since writeBits emits the most
    // significant of the [count] bits first, chunks enter the buffer with plain
    // shifts and without any per-write bit reversal. A full buffer is written out
    // as a single 64-bit word which is either already in MSB-first stream order or
    // brought into LSB-first order with a single reverseBits intrinsic.
    private fun emitWord(word: Long) {
        if (isMsbFirst) sink.writeULong(word.toULong()) else sink.writeULongLe(word.reverseBits().toULong())
        bitsEmitted += Long.SIZE_BITS
    }

    /**
     * Drains all buffered bits to the underlying sink, including a
     * trailing zero-padded partial byte if present.
     */
    private fun drainBytes() {
        var remaining = bitInBuffer
        var localBuffer = buffer
        while (remaining > 0) {
            val value = (localBuffer ushr (Long.SIZE_BITS - Byte.SIZE_BITS)).toUByte()
            sink.writeUByte(if (isMsbFirst) value else value.reverseBits())
            localBuffer = localBuffer shl Byte.SIZE_BITS
            bitsEmitted += Byte.SIZE_BITS
            remaining -= Byte.SIZE_BITS
        }
        buffer = 0L
        bitInBuffer = 0
    }

    override fun writeBits(count: Int, bits: ULong) {
        if (count == 0) return
        val offset = bitInBuffer
        // Top-align the chunk; bits above [count] are shifted out implicitly
        val chunk = bits.toLong() shl (Long.SIZE_BITS - count)
        val word = buffer or (chunk ushr offset)
        val total = offset + count
        if (total < Long.SIZE_BITS) {
            buffer = word
            bitInBuffer = total
            return
        }
        emitWord(word)
        // A shift by 64 is masked to zero on the JVM, so handle the aligned
        // full-word case explicitly and otherwise carry the remaining bits.
        buffer = if (offset == 0) 0L else chunk shl (Long.SIZE_BITS - offset)
        bitInBuffer = total - Long.SIZE_BITS
    }

    override fun padBits(count: Int, value: UByte) {
        val bits = if (value.toInt() and 0b1 == 0) 0UL else ULong.MAX_VALUE
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
        buffer = 0L
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