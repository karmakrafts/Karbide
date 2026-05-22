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
        // In MSB order, we read 0b1000_0000 so we get 128
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
    fun `Pad until next byte`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeBit(1U)
            sink.padUntilNextByte()
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
        // After closing, the partial byte (0b1000_0000 = 128) should be flushed
        assertEquals(128U.toUByte(), buffer.readUByte())
    }
}