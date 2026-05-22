package dev.karmakrafts.karbide

import kotlin.test.Test
import kotlin.test.assertEquals

class BitUtilsTest {
    @Test
    fun `Reverse Byte bits`() {
        assertEquals(0x80.toByte(), 0x01.toByte().reverseBits())
        assertEquals(0x01.toByte(), 0x80.toByte().reverseBits())
        assertEquals(0x0F.toByte(), 0xF0.toByte().reverseBits())
        assertEquals(0x55.toByte(), 0xAA.toByte().reverseBits())
    }

    @Test
    fun `Reverse Short bits`() {
        assertEquals(0x8000.toShort(), 0x0001.toShort().reverseBits())
        assertEquals(0x0001.toShort(), 0x8000.toShort().reverseBits())
        assertEquals(0x0FFF.toShort(), 0xFFF0.toShort().reverseBits())
        assertEquals(0x5555.toShort(), 0xAAAA.toShort().reverseBits())
    }

    @Test
    fun `Reverse Int bits`() {
        assertEquals(0x80000000.toInt(), 0x00000001.reverseBits())
        assertEquals(0x00000001, 0x80000000.toInt().reverseBits())
        assertEquals(0x0FFFFFFF, 0xFFFFFFF0.toInt().reverseBits())
        assertEquals(0x55555555, 0xAAAAAAAA.toInt().reverseBits())
    }

    @Test
    fun `Reverse Long bits`() {
        assertEquals(0x8000000000000000UL.toLong(), 0x0000000000000001L.reverseBits())
        assertEquals(0x0000000000000001L, 0x8000000000000000UL.toLong().reverseBits())
        assertEquals(0x0FFFFFFFFFFFFFFFUL.toLong(), 0xFFFFFFFFFFFFFFF0UL.toLong().reverseBits())
        assertEquals(0x5555555555555555L, 0xAAAAAAAAAAAAAAAAUL.toLong().reverseBits())
    }

    @Test
    fun `Reverse UByte bits`() {
        assertEquals(0x80U.toUByte(), 0x01U.toUByte().reverseBits())
        assertEquals(0x01U.toUByte(), 0x80U.toUByte().reverseBits())
        assertEquals(0x0FU.toUByte(), 0xF0U.toUByte().reverseBits())
        assertEquals(0x55U.toUByte(), 0xAAU.toUByte().reverseBits())
    }

    @Test
    fun `Reverse UShort bits`() {
        assertEquals(0x8000U.toUShort(), 0x0001U.toUShort().reverseBits())
        assertEquals(0x0001U.toUShort(), 0x8000U.toUShort().reverseBits())
        assertEquals(0x0FFFU.toUShort(), 0xFFF0U.toUShort().reverseBits())
        assertEquals(0x5555U.toUShort(), 0xAAAAU.toUShort().reverseBits())
    }

    @Test
    fun `Reverse UInt bits`() {
        assertEquals(0x80000000U, 0x00000001U.reverseBits())
        assertEquals(0x00000001U, 0x80000000U.reverseBits())
        assertEquals(0x0FFFFFFFU, 0xFFFFFFF0U.reverseBits())
        assertEquals(0x55555555U, 0xAAAAAAAAU.reverseBits())
    }

    @Test
    fun `Reverse ULong bits`() {
        assertEquals(0x8000000000000000UL, 0x0000000000000001UL.reverseBits())
        assertEquals(0x0000000000000001UL, 0x8000000000000000UL.reverseBits())
        assertEquals(0x0FFFFFFFFFFFFFFFUL, 0xFFFFFFFFFFFFFFF0UL.reverseBits())
        assertEquals(0x5555555555555555UL, 0xAAAAAAAAAAAAAAAAUL.reverseBits())
    }
}