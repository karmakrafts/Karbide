package dev.karmakrafts.karbide

import kotlinx.io.Buffer
import kotlinx.io.RawSink
import kotlinx.io.readByteArray

/**
 * A [RawSink] implementation that writes to a [ByteArray].
 */
class ByteArraySink : RawSink {
    private val buffer: Buffer = Buffer()

    /**
     * Write [byteCount] bytes from [source] into this sink.
     *
     * @param source The source to read from.
     * @param byteCount The number of bytes to write.
     */
    override fun write(source: Buffer, byteCount: Long) {
        buffer.write(source, byteCount)
    }

    /**
     * Reset the sink by clearing the internal buffer.
     */
    fun reset() {
        buffer.clear()
    }

    /**
     * Get the content of this sink as a [ByteArray].
     *
     * @return The content of this sink.
     */
    fun toByteArray(): ByteArray = buffer.readByteArray()

    /**
     * Flush the sink.
     */
    override fun flush() = Unit

    /**
     * Close the sink.
     */
    override fun close() {
        buffer.clear()
    }
}