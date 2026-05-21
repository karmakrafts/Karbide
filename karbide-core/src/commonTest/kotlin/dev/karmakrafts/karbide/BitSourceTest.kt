package dev.karmakrafts.karbide

import kotlinx.io.Buffer
import kotlinx.io.writeUByte
import kotlinx.io.writeUInt
import kotlinx.io.writeULong
import kotlinx.io.writeUShort
import kotlin.test.Test
import kotlin.test.assertEquals

class BitSourceTest {
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
            source.skipBit() // skip 0
            source.skipNibble() // skip 0xF
            assertEquals(0x12.toByte(), source.readByte())
            source.skipNibbles(2) // skip 0xA, 0xB
            source.skipByte() // skip 0x34
            source.skipBytes(1) // skip 0x56
        }
    }
}