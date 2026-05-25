package dev.karmakrafts.karbide

import kotlinx.io.Sink
import kotlinx.io.writeUByte
import kotlin.math.min

/**
 * Interface for writing individual bits to a sink.
 */
interface BitSink : AutoCloseable {
    /**
     * The bit order of this bit sink.
     */
    val bitOrder: BitOrder

    /**
     * The current byte offset in the sink.
     */
    val byte: Long

    /**
     * The current bit offset within the current byte (0-7).
     */
    val bit: Int

    /**
     * Write the specified number of bits to the sink.
     *
     * @param count The number of bits to write.
     * @param bits The bits to write as a [ULong].
     */
    fun writeBits(count: Int, bits: ULong)

    /**
     * Write the specified number of padding bits to the sink.
     *
     * @param count The number of bits to write.
     * @param value The value of the bits to write (0 or 1).
     */
    fun padBits(count: Int, value: UByte)

    /**
     * Write the specified number of padding bits to the sink.
     *
     * @param value The value of the bits to write (0 or 1).
     */
    fun padToNextByte(value: UByte)

    /**
     * Flush any remaining data buffered internally into the
     * underlying [Sink].
     */
    fun flush()
}

private data class BitSinkImpl( // @formatter:off
    private val sink: Sink,
    private val isSinkOwned: Boolean,
    override val bitOrder: BitOrder
) : BitSink { // @formatter:on
    private var isClosed: Boolean = false

    override var byte: Long = 0L
        private set

    override val bit: Int get() = bitInBuffer and 7 // % 8

    private var bitInBuffer: Int = 0
    private var buffer: ULong = 0UL

    private fun flushCurrentByte() {
        val value = if (bitOrder == BitOrder.MSB_FIRST) buffer.toUByte().reverseBits() else buffer.toUByte()
        sink.writeUByte(value)
    }

    /**
     * Drains all available whole bytes to the underlying sink.
     */
    private fun drainBytes() {
        while (bitInBuffer >= Byte.SIZE_BITS) {
            flushCurrentByte()
            buffer = buffer shr Byte.SIZE_BITS
            bitInBuffer -= Byte.SIZE_BITS
            byte++
        }
    }

    override fun writeBits(count: Int, bits: ULong) {
        var remaining = count
        var value = bits
        while (remaining > 0) {
            val space = ULong.SIZE_BITS - bitInBuffer
            val take = min(remaining, space)
            // Extract bits we want to move over
            val chunk = if (take == ULong.SIZE_BITS) value
            else {
                val mask = (1UL shl take) - 1UL
                value and mask
            }
            // Move the chunk into the buffer respecting the write order of this sink
            val shiftedChunk = chunk.reverseBits(take) shl bitInBuffer
            buffer = buffer or shiftedChunk

            // Update state
            bitInBuffer += take
            value = value shr take
            remaining -= take
            // Drain any whole accumulated bytes from the buffer
            drainBytes()
        }
    }

    override fun padBits(count: Int, value: UByte) {
        val bitValue = value.toULong() and 0b1UL
        val bits = when (bitValue) { // @formatter:off
            1UL -> when (count) {
                64 -> ULong.MAX_VALUE
                else -> (1UL shl count) - 1UL
            }
            else -> 0UL
        } // @formatter:on
        writeBits(count, bits)
    }

    override fun padToNextByte(value: UByte) {
        val count = (Byte.SIZE_BITS - bit) and 7
        if (count > 0) padBits(count, value)
    }

    override fun flush() {
        if (bit == 0) return
        flushCurrentByte()
    }

    override fun close() {
        if (isClosed) return
        flush()
        if (isSinkOwned) sink.close()
        byte = 0L
        bitInBuffer = 0
        isClosed = true
    }
}

/**
 * Create a new [BitSink] from the current [Sink].
 *
 * @param isSinkOwned Whether the [Sink] is owned by the [BitSink] and should be closed when it is.
 * @param bitOrder Whether bits are written out LSB or MSB first.
 * @return A new [BitSink] instance.
 */
fun Sink.bitSink(
    isSinkOwned: Boolean = true, bitOrder: BitOrder = BitOrder.MSB_FIRST
): BitSink = BitSinkImpl(this, isSinkOwned, bitOrder)