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

    override val byte: Long
        get() = ((emittedByteHigh.toUInt().toLong() shl Int.SIZE_BITS) or emittedByteLow.toUInt().toLong()) +
                (bitInBuffer shr 3)
    override val bit: Int get() = bitInBuffer and 7 // % 8

    // Keep the emitted-byte position split into two words so buffer writes do not
    // require Long arithmetic on 32-bit targets. It is joined only when the public
    // Long byte position is queried.
    private var emittedByteLow: Int = 0
    private var emittedByteHigh: Int = 0
    private var bitInBuffer: Int = 0
    private var buffer: Int = 0

    @Suppress("NOTHING_TO_INLINE")
    private inline fun addEmittedBytes(count: Int) {
        emittedByteLow += count
        if (emittedByteLow in 0 until count) emittedByteHigh++
    }

    // The buffer is top-aligned: the first bit written ends up at bit 31 and bits
    // below the buffered range are always zero. Since writeBits emits the most
    // significant of the [count] bits first, chunks enter the buffer with plain
    // shifts and without any per-write bit reversal. A full buffer is written out
    // as a single 32-bit word which is either already in MSB-first stream order or
    // brought into LSB-first order with a single reverseBits intrinsic.
    @Suppress("NOTHING_TO_INLINE")
    private inline fun emitWord(word: Int) {
        if (isMsbFirst) sink.writeUInt(word.toUInt()) else sink.writeUIntLe(word.reverseBits().toUInt())
        emittedByteLow += Int.SIZE_BYTES
        if (emittedByteLow == 0) emittedByteHigh++
    }

    /**
     * Drains all buffered bits to the underlying sink, including a
     * trailing zero-padded partial byte if present.
     */
    private fun drainBytes() {
        var remaining = bitInBuffer
        var localBuffer = buffer
        val byteCount = (remaining + 7) shr 3
        while (remaining > 0) {
            val value = (localBuffer ushr (Int.SIZE_BITS - Byte.SIZE_BITS)).toByte()
            sink.writeUByte(if (isMsbFirst) value.toUByte() else value.reverseBits().toUByte())
            localBuffer = localBuffer shl Byte.SIZE_BITS
            remaining -= Byte.SIZE_BITS
        }
        addEmittedBytes(byteCount)
        buffer = 0
        bitInBuffer = 0
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun writeChunk(count: Int, bits: Int) {
        val offset = bitInBuffer
        // Top-align the chunk; bits above [count] are shifted out implicitly
        val chunk = bits shl (Int.SIZE_BITS - count)
        val word = buffer or (chunk ushr offset)
        val total = offset + count
        if (total < Int.SIZE_BITS) {
            buffer = word
            bitInBuffer = total
            return
        }
        emitWord(word)
        buffer = if (offset == 0) 0 else chunk shl (Int.SIZE_BITS - offset)
        bitInBuffer = total - Int.SIZE_BITS
    }

    internal fun writeBits32(count: Int, bits: UInt) {
        require(count in 0..UInt.SIZE_BITS) { "count must be between 0 and ${UInt.SIZE_BITS}" }
        if (count == 0) return
        writeChunk(count, bits.toInt())
    }

    private fun writeWideBits(count: Int, bits: ULong) {
        val remaining = count - Int.SIZE_BITS
        writeChunk(Int.SIZE_BITS, (bits shr remaining).toInt())
        writeChunk(remaining, bits.toInt())
    }

    override fun writeBits(count: Int, bits: ULong) {
        if (count <= 0) {
            require(count == 0) { "count must not be negative" }
            return
        }
        if (count > Int.SIZE_BITS) {
            writeWideBits(count, bits)
            return
        }
        writeChunk(count, bits.toInt())
    }

    override fun padBits(count: Int, value: UByte) {
        val bits = if (value.toInt() and 0b1 == 1) -1 else 0
        var remaining = count
        while (remaining > Int.SIZE_BITS) {
            writeChunk(Int.SIZE_BITS, bits)
            remaining -= Int.SIZE_BITS
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
        buffer = 0
        bitInBuffer = 0
        emittedByteLow = 0
        emittedByteHigh = 0
    }

    override fun close() {
        if (isClosed) return
        flush()
        if (isSinkOwned) sink.close()
        isClosed = true
    }
}