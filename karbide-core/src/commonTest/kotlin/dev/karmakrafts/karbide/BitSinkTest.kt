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

import kotlinx.io.Buffer
import kotlinx.io.readByteArray
import kotlinx.io.readUByte
import kotlinx.io.readUInt
import kotlinx.io.readULong
import kotlinx.io.readUShort
import kotlin.test.Test
import kotlin.test.assertEquals

class BitSinkTest {
    @Test
    fun `Write an odd number of bits`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeNibble(0b0000U)
            sink.writeNibble(0b0011U)
            sink.writeBit(0b1U)
        }
        assertEquals(3U.toUByte(), buffer.readUByte())
        assertEquals(128U.toUByte(), buffer.readUByte())
    }

    @Test
    fun `Write bits within same byte`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeNibble(0b0000U)
            sink.writeNibble(0b0011U)
        }
        assertEquals(3U.toUByte(), buffer.readUByte())
    }

    @Test
    fun `Write bits beyond byte boundary`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeUByte(0U)
            sink.writeNibble(0b0000U)
            sink.writeNibble(0b0011U)
        }
        buffer.skip(UByte.SIZE_BYTES.toLong())
        assertEquals(3U.toUByte(), buffer.readUByte())
    }

    @Test
    fun `Write numeric types`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeByte(0x12)
            sink.writeShort(0x1234)
            sink.writeShortLe(0x1234)
            sink.writeInt(0x12345678)
            sink.writeIntLe(0x12345678)
            sink.writeLong(0x1234567890ABCDEFL)
            sink.writeLongLe(0x1234567890ABCDEFL)
            sink.writeFloat(1.234f)
            sink.writeFloatLe(1.234f)
            sink.writeDouble(1.23456789)
            sink.writeDoubleLe(1.23456789)
        }

        assertEquals(0x12.toByte(), buffer.readByte())
        assertEquals(0x1234.toShort(), buffer.readShort())
        assertEquals(0x1234.toShort(), buffer.readShort().reverseBytes())
        assertEquals(0x12345678, buffer.readInt())
        assertEquals(0x12345678, buffer.readInt().reverseBytes())
        assertEquals(0x1234567890ABCDEFL, buffer.readLong())
        assertEquals(0x1234567890ABCDEFL, buffer.readLong().reverseBytes())
        assertEquals(1.234f, Float.fromBits(buffer.readInt()), 0.001F)
        assertEquals(1.234f, Float.fromBits(buffer.readInt().reverseBytes()), 0.001F)
        assertEquals(1.23456789, Double.fromBits(buffer.readLong()))
        assertEquals(1.23456789, Double.fromBits(buffer.readLong().reverseBytes()))
    }

    @Test
    fun `Write unsigned numeric types`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeUByte(0x12U)
            sink.writeUShort(0x1234U)
            sink.writeUShortLe(0x1234U)
            sink.writeUInt(0x12345678U)
            sink.writeUIntLe(0x12345678U)
            sink.writeULong(0x1234567890ABCDEFUL)
            sink.writeULongLe(0x1234567890ABCDEFUL)
        }

        assertEquals(0x12U.toUByte(), buffer.readUByte())
        assertEquals(0x1234U.toUShort(), buffer.readUShort())
        assertEquals(0x1234U.toUShort(), buffer.readUShort().reverseBytes())
        assertEquals(0x12345678U, buffer.readUInt())
        assertEquals(0x12345678U, buffer.readUInt().reverseBytes())
        assertEquals(0x1234567890ABCDEFUL, buffer.readULong())
        assertEquals(0x1234567890ABCDEFUL, buffer.readULong().reverseBytes())
    }

    @Test
    fun `Write ULong beyond word boundary`() {
        val expected = byteArrayOf(
            0xA1.toByte(), 0x23, 0x45, 0x67, 0x89.toByte(), 0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte(), 0x05
        )
        for (bitOrder in BitOrder.entries) {
            val buffer = Buffer()
            buffer.bitSink(bitOrder = bitOrder).use { sink ->
                sink.writeNibble(0xAU)
                sink.writeULong(0x123456789ABCDEF0UL)
                sink.writeNibble(0x5U)
            }

            val actual = buffer.readByteArray(expected.size)
            for (i in expected.indices) {
                val expectedByte = if (bitOrder == BitOrder.MSB_FIRST) expected[i] else expected[i].reverseBits()
                assertEquals(expectedByte, actual[i])
            }
        }
    }

    @Test
    fun `Write repeated word boundaries`() {
        val expected = byteArrayOf(
            0x01, 0x23, 0x45, 0x67, 0x89.toByte(), 0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte(),
            0xF1.toByte(), 0x23, 0x45, 0x67, 0x89.toByte(), 0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte(),
            0x0F, 0xED.toByte(), 0xCB.toByte(), 0xA9.toByte(), 0x87.toByte(), 0x65, 0x43, 0x21, 0xA0.toByte()
        )
        for (bitOrder in BitOrder.entries) {
            val buffer = Buffer()
            buffer.bitSink(bitOrder = bitOrder).use { sink ->
                sink.writeBits(0, ULong.MAX_VALUE)
                sink.writeBits(64, 0x0123456789ABCDEFUL)
                sink.writeBits(4, ULong.MAX_VALUE)
                sink.writeBits(64, 0x123456789ABCDEF0UL)
                sink.writeBits(60, 0xAFEDCBA987654321UL)
                sink.writeBits(3, 0b101UL)
                assertEquals(24L, sink.byte)
                assertEquals(3, sink.bit)
            }

            val actual = buffer.readByteArray(expected.size)
            for (i in expected.indices) {
                val expectedByte = if (bitOrder == BitOrder.MSB_FIRST) expected[i] else expected[i].reverseBits()
                assertEquals(expectedByte, actual[i])
            }
        }
    }

    @Test
    fun `Write byte array`() {
        val buffer = Buffer()
        val data = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05)
        buffer.bitSink().use { sink ->
            sink.writeByteArray(data)
        }
        val readData = buffer.readByteArray(data.size)
        for (i in data.indices) {
            assertEquals(data[i], readData[i])
        }
    }

    @Test
    fun `Write padding`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeBit(1U)
            sink.padBit(0U)
            sink.padNibble(1U)
            sink.padNibbles(2, 0U)
            sink.padByte(1U)
            sink.padBytes(2, 0U)
        }

        assertEquals(0xBC.toUByte(), buffer.readUByte())
        assertEquals(0x03.toUByte(), buffer.readUByte())
        assertEquals(0xFC.toUByte(), buffer.readUByte())
        assertEquals(0x00.toUByte(), buffer.readUByte())
        assertEquals(0x00.toUByte(), buffer.readUByte())
    }

    @Test
    fun `Write padding beyond word boundary`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeNibble(0U)
            sink.padBits(64, 1U)
            sink.writeNibble(0U)
        }

        assertEquals(0x0FU, buffer.readUByte().toUInt())
        repeat(7) {
            assertEquals(0xFFU, buffer.readUByte().toUInt())
        }
        assertEquals(0xF0U, buffer.readUByte().toUInt())
    }

    @Test
    fun `Pad until next byte`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeBit(1U)
            sink.padToNextByte(0U)
            sink.writeBit(1U)
            sink.padToNextByte(1U)
        }

        assertEquals(0x80.toUByte(), buffer.readUByte())
        assertEquals(0xFF.toUByte(), buffer.readUByte())
    }

    @Test
    fun `Flush on close`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeBit(1U)
        }
        assertEquals(128U.toUByte(), buffer.readUByte())
    }

    @Test
    fun `Write bits in LSB order`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeBitsLsb(4, 0b0011UL)
            sink.writeBitsLsb(4, 0b1010UL)
        }
        assertEquals(0xC5U.toUByte(), buffer.readUByte())
    }

    @Test
    fun `Write bits in LSB order beyond byte boundary`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeBitsLsb(12, 0b1011_0100_1110UL)
        }
        assertEquals(0x72U.toUByte(), buffer.readUByte())
        assertEquals(0xD0U.toUByte(), buffer.readUByte())
    }

    @Test
    fun `Write numeric types in LSB order`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeByteLsb(0x12)
            sink.writeShortLsb(0x1234)
            sink.writeIntLsb(0x12345678)
            sink.writeLongLsb(0x1234567890ABCDEFL)
            sink.writeFloatLsb(1.234f)
            sink.writeDoubleLsb(1.23456789)
        }

        assertEquals(0x12.toByte().reverseBits(), buffer.readByte())
        assertEquals(0x1234.toShort().reverseBits(), buffer.readShort())
        assertEquals(0x12345678.reverseBits(), buffer.readInt())
        assertEquals(0x1234567890ABCDEFL.reverseBits(), buffer.readLong())
        assertEquals(1.234f, Float.fromBits(buffer.readInt().reverseBits()), 0.001F)
        assertEquals(1.23456789, Double.fromBits(buffer.readLong().reverseBits()))
    }

    @Test
    fun `Write unsigned numeric types in LSB order`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeUByteLsb(0x12U)
            sink.writeUShortLsb(0x1234U)
            sink.writeUIntLsb(0x12345678U)
            sink.writeULongLsb(0x1234567890ABCDEFUL)
        }

        assertEquals(0x12U.toUByte().reverseBits(), buffer.readUByte())
        assertEquals(0x1234U.toUShort().reverseBits(), buffer.readUShort())
        assertEquals(0x12345678U.reverseBits(), buffer.readUInt())
        assertEquals(0x1234567890ABCDEFUL.reverseBits(), buffer.readULong())
    }

    @Test
    fun `Write byte array and string in LSB order`() {
        val buffer = Buffer()
        val data = byteArrayOf(0x01, 0x02, 0x03)
        buffer.bitSink().use { sink ->
            sink.writeByteArrayLsb(data)
            sink.writeStringLsb("ABC")
        }

        for (b in data) {
            assertEquals(b.reverseBits(), buffer.readByte())
        }
        for (c in "ABC") {
            assertEquals(c.code.toByte().reverseBits(), buffer.readByte())
        }
    }

    @Test
    fun `Write numeric types in LE LSB order`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeShortLeLsb(0x1234)
            sink.writeIntLeLsb(0x12345678)
            sink.writeLongLeLsb(0x1234567890ABCDEFL)
            sink.writeFloatLeLsb(1.234f)
            sink.writeDoubleLeLsb(1.23456789)
        }

        assertEquals(0x1234.toShort().reverseBytes().reverseBits(), buffer.readShort())
        assertEquals(0x12345678.reverseBytes().reverseBits(), buffer.readInt())
        assertEquals(0x1234567890ABCDEFL.reverseBytes().reverseBits(), buffer.readLong())
        assertEquals(1.234f, Float.fromBits(buffer.readInt().reverseBits().reverseBytes()), 0.001F)
        assertEquals(1.23456789, Double.fromBits(buffer.readLong().reverseBits().reverseBytes()))
    }

    @Test
    fun `Write unsigned numeric types in LE LSB order`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeUShortLeLsb(0x1234U)
            sink.writeUIntLeLsb(0x12345678U)
            sink.writeULongLeLsb(0x1234567890ABCDEFUL)
        }

        assertEquals(0x1234U.toUShort().reverseBytes().reverseBits(), buffer.readUShort())
        assertEquals(0x12345678U.reverseBytes().reverseBits(), buffer.readUInt())
        assertEquals(0x1234567890ABCDEFUL.reverseBytes().reverseBits(), buffer.readULong())
    }

    @Test
    fun `Write bits with LSB first bit order`() {
        val buffer = Buffer()
        buffer.bitSink(bitOrder = BitOrder.LSB_FIRST).use { sink ->
            sink.writeBits(4, 0b1011UL)
            sink.writeBits(4, 0b0101UL)
        }
        assertEquals(0xADU.toUByte(), buffer.readUByte())
    }
}