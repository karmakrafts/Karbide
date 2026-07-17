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
import kotlinx.io.EOFException
import kotlinx.io.writeUByte
import kotlinx.io.writeUInt
import kotlinx.io.writeULong
import kotlinx.io.writeUShort
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BitSourceTest {
    @Test
    fun `Read an odd number of bits`() {
        val buffer = Buffer()
        buffer.writeUByte(0b0000_0011U)
        buffer.writeUByte(0b1000_0000U)
        buffer.bitSource().use { source ->
            assertEquals(0U.toUByte(), source.readNibble())
            assertEquals(3U.toUByte(), source.readNibble())
            assertEquals(1U.toUByte(), source.readBit())
        }
    }

    @Test
    fun `Read bits within same byte`() {
        val buffer = Buffer()
        buffer.writeUByte(0b0000_0011U)
        buffer.bitSource().use { source ->
            source.skipNibble()
            assertEquals(3U.toUByte(), source.readNibble())
        }
    }

    @Test
    fun `Read bits beyond byte boundary`() {
        val buffer = Buffer()
        buffer.writeUByte(0b0000_0000U)
        buffer.writeUByte(0b0000_0000U)
        buffer.writeUByte(0b0000_0011U)
        buffer.bitSource().use { source ->
            source.skipBits(UShort.SIZE_BITS)
            assertEquals(3U.toUByte(), source.readUByte())
        }
    }

    @Test
    fun `Read numeric types`() {
        val buffer = Buffer()
        buffer.writeByte(0x12)
        buffer.writeShort(0x1234)
        buffer.writeShort(0x1234.toShort().reverseBytes())
        buffer.writeInt(0x12345678)
        buffer.writeInt(0x12345678.reverseBytes())
        buffer.writeLong(0x1234567890ABCDEFL)
        buffer.writeLong(0x1234567890ABCDEFL.reverseBytes())
        buffer.writeInt(1.234f.toRawBits())
        buffer.writeInt(1.234f.toRawBits().reverseBytes())
        buffer.writeLong(1.23456789.toRawBits())
        buffer.writeLong(1.23456789.toRawBits().reverseBytes())

        buffer.bitSource().use { source ->
            assertEquals(0x12.toByte(), source.readByte())
            assertEquals(0x1234.toShort(), source.readShort())
            assertEquals(0x1234.toShort(), source.readShortLe())
            assertEquals(0x12345678, source.readInt())
            assertEquals(0x12345678, source.readIntLe())
            assertEquals(0x1234567890ABCDEFL, source.readLong())
            assertEquals(0x1234567890ABCDEFL, source.readLongLe())
            assertEquals(1.234f, source.readFloat(), 0.001F)
            assertEquals(1.234f, source.readFloatLe(), 0.001F)
            assertEquals(1.23456789, source.readDouble())
            assertEquals(1.23456789, source.readDoubleLe())
        }
    }

    @Test
    fun `Read unsigned numeric types`() {
        val buffer = Buffer()
        buffer.writeUByte(0x12U)
        buffer.writeUShort(0x1234U)
        buffer.writeUShort(0x1234U.toUShort().reverseBytes())
        buffer.writeUInt(0x12345678U)
        buffer.writeUInt(0x12345678U.reverseBytes())
        buffer.writeULong(0x1234567890ABCDEFUL)
        buffer.writeULong(0x1234567890ABCDEFUL.reverseBytes())

        buffer.bitSource().use { source ->
            assertEquals(0x12U.toUByte(), source.readUByte())
            assertEquals(0x1234U.toUShort(), source.readUShort())
            assertEquals(0x1234U.toUShort(), source.readUShortLe())
            assertEquals(0x12345678U, source.readUInt())
            assertEquals(0x12345678U, source.readUIntLe())
            assertEquals(0x1234567890ABCDEFUL, source.readULong())
            assertEquals(0x1234567890ABCDEFUL, source.readULongLe())
        }
    }

    @Test
    fun `Read byte array`() {
        val buffer = Buffer()
        val data = byteArrayOf(0x01, 0x02)
        buffer.write(data)

        buffer.bitSource().use { source ->
            val bytes = source.readByteArray(2)
            assertEquals(0x01, bytes[0])
            assertEquals(0x02, bytes[1])
        }
    }

    @Test
    fun `Skip bits nibbles and bytes`() {
        val buffer = Buffer()
        buffer.writeUByte(0xBCU)
        buffer.writeUByte(0x4AU)
        buffer.writeUByte(0xACU)
        buffer.writeUByte(0xD1U)
        buffer.writeUByte(0x58U)

        buffer.bitSource().use { source ->
            assertEquals(1U.toUByte(), source.readBit())
            source.skipBit()
            source.skipNibble()
            assertEquals(0x12.toByte(), source.readByte())
            source.skipNibbles(2)
            source.skipByte()
            source.skipBytes(1)
        }
    }

    @Test
    fun `Skip until next byte`() {
        val buffer = Buffer()
        buffer.writeUByte(0b1011_1111U)
        buffer.writeUByte(0x4AU)
        buffer.bitSource().use { source ->
            assertEquals(1U.toUByte(), source.readBit())
            source.skipUntilNextByte()
            assertEquals(0x4AU.toUByte(), source.readUByte())
        }
    }

    @Test
    fun `Read bits in LSB order`() {
        val buffer = Buffer()
        buffer.writeUByte(0xC5U)
        buffer.bitSource().use { source ->
            assertEquals(0b0011UL, source.readBitsLsb(4))
            assertEquals(0b1010UL, source.readBitsLsb(4))
        }
    }

    @Test
    fun `Read bits in LSB order beyond byte boundary`() {
        val buffer = Buffer()
        buffer.writeUByte(0x72U)
        buffer.writeUByte(0xD0U)
        buffer.bitSource().use { source ->
            assertEquals(0b1011_0100_1110UL, source.readBitsLsb(12))
        }
    }

    @Test
    fun `Read numeric types in LSB order`() {
        val buffer = Buffer()
        buffer.writeByte(0x12.toByte().reverseBits())
        buffer.writeShort(0x1234.toShort().reverseBits())
        buffer.writeInt(0x12345678.reverseBits())
        buffer.writeLong(0x1234567890ABCDEFL.reverseBits())
        buffer.writeInt(1.234f.toRawBits().reverseBits())
        buffer.writeLong(1.23456789.toRawBits().reverseBits())

        buffer.bitSource().use { source ->
            assertEquals(0x12.toByte(), source.readByteLsb())
            assertEquals(0x1234.toShort(), source.readShortLsb())
            assertEquals(0x12345678, source.readIntLsb())
            assertEquals(0x1234567890ABCDEFL, source.readLongLsb())
            assertEquals(1.234f, source.readFloatLsb(), 0.001F)
            assertEquals(1.23456789, source.readDoubleLsb())
        }
    }

    @Test
    fun `Read unsigned numeric types in LSB order`() {
        val buffer = Buffer()
        buffer.writeUByte(0x12U.toUByte().reverseBits())
        buffer.writeUShort(0x1234U.toUShort().reverseBits())
        buffer.writeUInt(0x12345678U.reverseBits())
        buffer.writeULong(0x1234567890ABCDEFUL.reverseBits())

        buffer.bitSource().use { source ->
            assertEquals(0x12U.toUByte(), source.readUByteLsb())
            assertEquals(0x1234U.toUShort(), source.readUShortLsb())
            assertEquals(0x12345678U, source.readUIntLsb())
            assertEquals(0x1234567890ABCDEFUL, source.readULongLsb())
        }
    }

    @Test
    fun `Read byte array and string in LSB order`() {
        val buffer = Buffer()
        val data = byteArrayOf(0x01, 0x02, 0x03)
        for (b in data) {
            buffer.writeByte(b.reverseBits())
        }
        for (c in "ABC") {
            buffer.writeByte(c.code.toByte().reverseBits())
        }

        buffer.bitSource().use { source ->
            val readData = source.readByteArrayLsb(data.size)
            for (i in data.indices) {
                assertEquals(data[i], readData[i])
            }
            assertEquals("ABC", source.readStringLsb(3))
        }
    }

    @Test
    fun `Read numeric types in LE LSB order`() {
        val buffer = Buffer()
        buffer.writeShort(0x1234.toShort().reverseBytes().reverseBits())
        buffer.writeInt(0x12345678.reverseBytes().reverseBits())
        buffer.writeLong(0x1234567890ABCDEFL.reverseBytes().reverseBits())
        buffer.writeInt(1.234f.toRawBits().reverseBytes().reverseBits())
        buffer.writeLong(1.23456789.toRawBits().reverseBytes().reverseBits())

        buffer.bitSource().use { source ->
            assertEquals(0x1234.toShort(), source.readShortLeLsb())
            assertEquals(0x12345678, source.readIntLeLsb())
            assertEquals(0x1234567890ABCDEFL, source.readLongLeLsb())
            assertEquals(1.234f, source.readFloatLeLsb(), 0.001F)
            assertEquals(1.23456789, source.readDoubleLeLsb())
        }
    }

    @Test
    fun `Read unsigned numeric types in LE LSB order`() {
        val buffer = Buffer()
        buffer.writeUShort(0x1234U.toUShort().reverseBytes().reverseBits())
        buffer.writeUInt(0x12345678U.reverseBytes().reverseBits())
        buffer.writeULong(0x1234567890ABCDEFUL.reverseBytes().reverseBits())

        buffer.bitSource().use { source ->
            assertEquals(0x1234U.toUShort(), source.readUShortLeLsb())
            assertEquals(0x12345678U, source.readUIntLeLsb())
            assertEquals(0x1234567890ABCDEFUL, source.readULongLeLsb())
        }
    }

    @Test
    fun `Read bits with LSB first bit order`() {
        val buffer = Buffer()
        buffer.writeUByte(0xADU)
        buffer.bitSource(bitOrder = BitOrder.LSB_FIRST).use { source ->
            assertEquals(0b1011UL, source.readBits(4))
            assertEquals(0b0101UL, source.readBits(4))
        }
    }

    @Test
    fun `Read 64 bits across buffer boundaries`() {
        val buffer = Buffer()
        buffer.writeUByte(0xD2U)
        buffer.writeULong(0x3456789ABCDEF080UL)
        buffer.bitSource().use { source ->
            assertEquals(1UL, source.readBits(1))
            assertEquals(0xA468ACF13579BDE1UL, source.readBits(64))
            assertEquals(1, source.bit)
            assertEquals(8L, source.byte)
        }
    }

    @Test
    fun `Read bits throws before consuming partial data`() {
        val buffer = Buffer()
        buffer.writeUByte(0xACU)
        buffer.bitSource().use { source ->
            assertFalse(source.requestBits(9))
            assertFailsWith<EOFException> { source.readBits(9) }
            assertEquals(0L, source.byte)
            assertEquals(0, source.bit)
            assertEquals(0xACUL, source.readBits(8))
            assertTrue(source.exhausted)
        }
    }

    @Test
    fun `Skip bits throws before consuming partial data`() {
        val buffer = Buffer()
        buffer.writeUByte(0xACU)
        buffer.bitSource().use { source ->
            assertFailsWith<EOFException> { source.skipBits(9) }
            assertEquals(0L, source.byte)
            assertEquals(0, source.bit)
            assertEquals(0xACUL, source.readBits(8))
            assertTrue(source.exhausted)
        }
    }

    @Test
    fun `Peek bits does not consume data`() {
        val buffer = Buffer()
        buffer.writeUByte(0b1100_1010U)
        buffer.writeUByte(0b0110_0000U)
        buffer.bitSource().use { source ->
            assertEquals(0b110UL, source.readBits(3))
            assertTrue(source.requestBits(10))
            assertFalse(source.requestBits(14))
            assertEquals(0b01010UL, source.peekBits(5))
            assertEquals(0L, source.byte)
            assertEquals(3, source.bit)
            assertEquals(0b01010UL, source.readBits(5))
            assertEquals(0b0110UL, source.readBits(4))
        }
    }

    @Test
    fun `Peek 64 bits does not consume data`() {
        val buffer = Buffer()
        buffer.writeULong(0x1234567890ABCDEFUL)
        buffer.bitSource().use { source ->
            assertEquals(0x1234567890ABCDEFUL, source.peekBits(64))
            assertEquals(0L, source.byte)
            assertEquals(0, source.bit)
            assertEquals(0x1234567890ABCDEFUL, source.readBits(64))
        }
    }

    @Test
    fun `Peek 64 bits across buffer boundaries`() {
        val buffer = Buffer()
        buffer.writeUByte(0xD2U)
        buffer.writeULong(0x3456789ABCDEF080UL)
        buffer.bitSource().use { source ->
            assertEquals(1UL, source.readBits(1))
            assertEquals(0xA468ACF13579BDE1UL, source.peekBits(64))
            assertEquals(0L, source.byte)
            assertEquals(1, source.bit)
            assertEquals(0xA468ACF13579BDE1UL, source.readBits(64))
        }
    }
}