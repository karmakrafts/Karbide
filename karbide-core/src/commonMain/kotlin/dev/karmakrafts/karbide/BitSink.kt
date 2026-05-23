package dev.karmakrafts.karbide

import kotlinx.io.Sink
import kotlinx.io.writeUByte
import kotlin.math.max

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
     * Write the specified number of padding bits (0) to the sink until the next byte boundary.
     */
    fun padUntilNextByte()
}

private data class BitSinkImpl( // @formatter:off
    private val sink: Sink,
    private val isSinkOwned: Boolean,
    override val bitOrder: BitOrder
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

    private val writeNextBit: (UByte) -> Unit = if (bitOrder == BitOrder.MSB_FIRST) ::writeNextBitMsb
    else ::writeNextBitLsb

    private fun writeNextBitLsb(value: UByte) {
        currentByte = (currentByte.toUInt() or ((value.toUInt() and 0b1U) shl bit)).toUByte()
        if (bit < LAST_BIT) bit++
        else {
            sink.writeUByte(currentByte)
            currentByte = 0U.toUByte()
            bit = 0
            byte++
        }
    }

    private fun writeNextBitMsb(value: UByte) {
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
        if (count == 0) return
        val lastBit = count - 1
        for (bitIndex in 0..<count) {
            val toShift = max(0, lastBit - bitIndex)
            val bit = ((bits shr toShift) and 0b1UL).toUByte()
            writeNextBit(bit)
        }
    }

    override fun padBits(count: Int, value: UByte) {
        repeat(count) {
            writeNextBit(value)
        }
    }

    override fun padToNextByte(value: UByte) {
        padBits(LAST_BIT - bit + 1, value)
    }

    override fun padUntilNextByte() = padToNextByte(0U)

    override fun close() {
        if (isClosed) return
        if (bit != 0) sink.writeUByte(currentByte)
        if (isSinkOwned) sink.close()
        byte = 0L
        bit = 0
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