package dev.karmakrafts.karbide

import kotlinx.io.Buffer
import kotlinx.io.readByteArray
import kotlinx.io.readUByte
import kotlinx.io.readUInt
import kotlinx.io.readULong
import kotlinx.io.readUShort
import kotlin.test.Test
import kotlin.test.assertEquals

class BitWriterTest {
    @Test
    fun `Write bits within same byte`() {
        val buffer = Buffer()
        buffer.bitWriter().use { writer ->
            writer.writeNibble(0b0000U)
            writer.writeNibble(0b0011U)
        }
        assertEquals(3U.toUByte(), buffer.readUByte())
    }

    @Test
    fun `Write bits beyond byte boundary`() {
        val buffer = Buffer()
        buffer.bitWriter().use { writer ->
            writer.writeUByte(0U)
            writer.writeNibble(0b0000U)
            writer.writeNibble(0b0011U)
        }
        buffer.skip(UByte.SIZE_BYTES.toLong())
        assertEquals(3U.toUByte(), buffer.readUByte())
    }

    @Test
    fun `Write numeric types`() {
        val buffer = Buffer()
        buffer.bitWriter().use { writer ->
            writer.writeByte(0x12)
            writer.writeShort(0x1234)
            writer.writeShortLe(0x1234)
            writer.writeInt(0x12345678)
            writer.writeIntLe(0x12345678)
            writer.writeLong(0x1234567890ABCDEFL)
            writer.writeLongLe(0x1234567890ABCDEFL)
            writer.writeFloat(1.234f)
            writer.writeFloatLe(1.234f)
            writer.writeDouble(1.23456789)
            writer.writeDoubleLe(1.23456789)
        }

        assertEquals(0x12.toByte(), buffer.readByte())
        assertEquals(0x1234.toShort(), buffer.readShort())
        assertEquals(0x1234.toShort(), buffer.readShort().reverseBytes())
        assertEquals(0x12345678, buffer.readInt())
        assertEquals(0x12345678, buffer.readInt().reverseBytes())
        assertEquals(0x1234567890ABCDEFL, buffer.readLong())
        assertEquals(0x1234567890ABCDEFL, buffer.readLong().reverseBytes())
        assertEquals(1.234f, Float.fromBits(buffer.readInt()))
        assertEquals(1.234f, Float.fromBits(buffer.readInt().reverseBytes()))
        assertEquals(1.23456789, Double.fromBits(buffer.readLong()))
        assertEquals(1.23456789, Double.fromBits(buffer.readLong().reverseBytes()))
    }

    @Test
    fun `Write unsigned numeric types`() {
        val buffer = Buffer()
        buffer.bitWriter().use { writer ->
            writer.writeUByte(0x12U)
            writer.writeUShort(0x1234U)
            writer.writeUShortLe(0x1234U)
            writer.writeUInt(0x12345678U)
            writer.writeUIntLe(0x12345678U)
            writer.writeULong(0x1234567890ABCDEFUL)
            writer.writeULongLe(0x1234567890ABCDEFUL)
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
    fun `Write byte array`() {
        val buffer = Buffer()
        val data = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05)
        buffer.bitWriter().use { writer ->
            writer.writeByteArray(data)
        }
        val readData = buffer.readByteArray(data.size)
        for (i in data.indices) {
            assertEquals(data[i], readData[i])
        }
    }

    @Test
    fun `Write padding`() {
        val buffer = Buffer()
        buffer.bitWriter().use { writer ->
            writer.writeBit(1U)
            writer.padBit(0U)
            writer.padNibble(1U)
            writer.padNibbles(2, 0U)
            writer.padByte(1U)
            writer.padBytes(2, 0U)
        }

        assertEquals(0xBC.toUByte(), buffer.readUByte())
        assertEquals(0x03.toUByte(), buffer.readUByte())
        assertEquals(0xFC.toUByte(), buffer.readUByte())
        assertEquals(0x00.toUByte(), buffer.readUByte())
        assertEquals(0x00.toUByte(), buffer.readUByte())
    }
}