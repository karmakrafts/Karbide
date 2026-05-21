package dev.karmakrafts.karbide

import kotlinx.io.Buffer
import kotlinx.io.writeUByte
import kotlinx.io.writeUInt
import kotlinx.io.writeULong
import kotlinx.io.writeUShort
import kotlin.test.Test
import kotlin.test.assertEquals

class BitReaderTest {
    @Test
    fun `Read bits within same byte`() {
        val buffer = Buffer()
        buffer.writeUByte(0b0000_0011U)
        buffer.bitReader().use { reader ->
            reader.skipNibble()
            assertEquals(3U.toUByte(), reader.readNibble())
        }
    }

    @Test
    fun `Read bits beyond byte boundary`() {
        val buffer = Buffer()
        buffer.writeUByte(0b0000_0000U)
        buffer.writeUByte(0b0000_0000U)
        buffer.writeUByte(0b0000_0011U)
        buffer.bitReader().use { reader ->
            reader.skipBits(UShort.SIZE_BITS)
            assertEquals(3U.toUByte(), reader.readUByte())
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

        buffer.bitReader().use { reader ->
            assertEquals(0x12.toByte(), reader.readByte())
            assertEquals(0x1234.toShort(), reader.readShort())
            assertEquals(0x1234.toShort(), reader.readShortLe())
            assertEquals(0x12345678, reader.readInt())
            assertEquals(0x12345678, reader.readIntLe())
            assertEquals(0x1234567890ABCDEFL, reader.readLong())
            assertEquals(0x1234567890ABCDEFL, reader.readLongLe())
            assertEquals(1.234f, reader.readFloat())
            assertEquals(1.234f, reader.readFloatLe())
            assertEquals(1.23456789, reader.readDouble())
            assertEquals(1.23456789, reader.readDoubleLe())
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

        buffer.bitReader().use { reader ->
            assertEquals(0x12U.toUByte(), reader.readUByte())
            assertEquals(0x1234U.toUShort(), reader.readUShort())
            assertEquals(0x1234U.toUShort(), reader.readUShortLe())
            assertEquals(0x12345678U, reader.readUInt())
            assertEquals(0x12345678U, reader.readUIntLe())
            assertEquals(0x1234567890ABCDEFUL, reader.readULong())
            assertEquals(0x1234567890ABCDEFUL, reader.readULongLe())
        }
    }

    @Test
    fun `Read byte array`() {
        val buffer = Buffer()
        val data = byteArrayOf(0x01, 0x02)
        buffer.write(data)

        buffer.bitReader().use { reader ->
            val bytes = reader.readByteArray(2)
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

        buffer.bitReader().use { reader ->
            assertEquals(1U.toUByte(), reader.readBit())
            reader.skipBit() // skip 0
            reader.skipNibble() // skip 0xF
            assertEquals(0x12.toByte(), reader.readByte())
            reader.skipNibbles(2) // skip 0xA, 0xB
            reader.skipByte() // skip 0x34
            reader.skipBytes(1) // skip 0x56
        }
    }
}