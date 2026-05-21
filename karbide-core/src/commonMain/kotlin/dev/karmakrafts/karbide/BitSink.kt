package dev.karmakrafts.karbide

import kotlinx.io.Sink
import kotlinx.io.writeUByte

/**
 * Interface for writing individual bits to a sink.
 */
interface BitSink : AutoCloseable {
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
}

private data class BitSinkImpl( // @formatter:off
    private val sink: Sink,
    private val isSinkOwned: Boolean
) : BitSink { // @formatter:on
    companion object {
        private const val LAST_BIT: Int = Byte.SIZE_BITS - 1
    }

    private var isClosed: Boolean = false

    override var byte: Long = 0L
        private set
    override var bit: Int = 0
        private set

    private var currentByte: UByte = 0U.toUByte()

    private fun writeNextBit(value: UByte) {
        val bitIndex = LAST_BIT - bit
        currentByte = (currentByte.toUInt() or ((value.toUInt() and 0b1U) shl bitIndex)).toUByte()
        if (bit < LAST_BIT) bit++
        else {
            sink.writeUByte(currentByte)
            currentByte = 0U.toUByte()
            bit = 0
            byte++
        }
    }

    override fun writeBits(count: Int, bits: ULong) {
        val lastBit = count - 1
        for (bitIndex in 0..<count) {
            val bit = ((bits shr (lastBit - bitIndex)) and 0b1UL).toUByte()
            writeNextBit(bit)
        }
    }

    override fun padBits(count: Int, value: UByte) {
        repeat(count) {
            writeNextBit(value)
        }
    }

    override fun close() {
        if (isClosed) return
        if (isSinkOwned) sink.close()
        if (bit != LAST_BIT) { // If we aren't exactly on the last bit of the last byte..
            // ..flush last unfinished byte to the sink
            sink.writeUByte(currentByte)
        }
        byte = 0L
        bit = 0
        isClosed = true
    }
}

/**
 * Create a new [BitSink] from the current [Sink].
 *
 * @param isSinkOwned Whether the [Sink] is owned by the [BitSink] and should be closed when it is.
 * @return A new [BitSink] instance.
 */
fun Sink.bitSink(
    isSinkOwned: Boolean = true
): BitSink = BitSinkImpl(this, isSinkOwned)