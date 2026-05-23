package dev.karmakrafts.karbide

import kotlinx.io.Buffer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ByteArraySinkTest {

    @Test
    fun `Write to sink and convert to byte array`() {
        val sink = ByteArraySink()
        val buffer = Buffer()
        buffer.write(byteArrayOf(1, 2, 3, 4, 5))
        sink.write(buffer, 5)

        val result = sink.toByteArray()
        assertEquals(5, result.size)
        assertTrue(byteArrayOf(1, 2, 3, 4, 5).contentEquals(result))
    }

    @Test
    fun `Reset sink`() {
        val sink = ByteArraySink()
        val buffer = Buffer()
        buffer.write(byteArrayOf(1, 2, 3))
        sink.write(buffer, 3)
        sink.reset()

        assertEquals(0, sink.toByteArray().size)
    }

    @Test
    fun `Close sink`() {
        val sink = ByteArraySink()
        val buffer = Buffer()
        buffer.write(byteArrayOf(1, 2, 3))
        sink.write(buffer, 3)
        sink.close()

        assertEquals(0, sink.toByteArray().size)
    }

    @Test
    fun `Write multiple times`() {
        val sink = ByteArraySink()
        val buffer = Buffer()
        buffer.write(byteArrayOf(1, 2))
        sink.write(buffer, 2)
        buffer.write(byteArrayOf(3, 4))
        sink.write(buffer, 2)

        val result = sink.toByteArray()
        assertEquals(4, result.size)
        assertTrue(byteArrayOf(1, 2, 3, 4).contentEquals(result))
    }

    @Test
    fun `toByteArray consumes the buffer`() {
        val sink = ByteArraySink()
        val buffer = Buffer()
        buffer.write(byteArrayOf(1, 2, 3))
        sink.write(buffer, 3)

        val result1 = sink.toByteArray()
        assertEquals(3, result1.size)

        val result2 = sink.toByteArray()
        assertEquals(0, result2.size)
    }
}
