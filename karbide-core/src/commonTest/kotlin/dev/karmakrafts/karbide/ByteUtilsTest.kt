package dev.karmakrafts.karbide

import kotlin.test.Test
import kotlin.test.assertEquals

class ByteUtilsTest {
    @Test
    fun `Reverse Short bytes`() {
        assertEquals(0x3412.toShort(), 0x1234.toShort().reverseBytes())
        assertEquals(0x0000.toShort(), 0x0000.toShort().reverseBytes())
        assertEquals(0xFFFF.toShort(), 0xFFFF.toShort().reverseBytes())
    }

    @Test
    fun `Reverse Int bytes`() {
        assertEquals(0x78563412, 0x12345678.reverseBytes())
        assertEquals(0x00000000, 0x00000000.reverseBytes())
        assertEquals(-1, (-1).reverseBytes())
    }

    @Test
    fun `Reverse Long bytes`() {
        assertEquals(0xEFCDAB9078563412UL.toLong(), 0x1234567890ABCDEFL.reverseBytes())
        assertEquals(0L, 0L.reverseBytes())
        assertEquals(-1L, (-1L).reverseBytes())
    }

    @Test
    fun `Reverse UShort bytes`() {
        assertEquals(0x3412U.toUShort(), 0x1234U.toUShort().reverseBytes())
    }

    @Test
    fun `Reverse UInt bytes`() {
        assertEquals(0x78563412U, 0x12345678U.reverseBytes())
    }

    @Test
    fun `Reverse ULong bytes`() {
        assertEquals(0xEFCDAB9078563412UL, 0x1234567890ABCDEFUL.reverseBytes())
    }
}