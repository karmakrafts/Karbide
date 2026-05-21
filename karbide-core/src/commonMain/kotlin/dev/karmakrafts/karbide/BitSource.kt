package dev.karmakrafts.karbide

import kotlinx.io.Source
import kotlinx.io.readUByte

/**
 * Interface for reading individual bits from a source.
 */
interface BitSource : AutoCloseable {
    /**
     * The current byte offset in the source.
     */
    val byte: Long

    /**
     * The current bit offset within the current byte (0-7).
     */
    val bit: Int

    /**
     * Whether the source has been exhausted and all bits have been read.
     */
    val exhausted: Boolean

    /**
     * Read the specified number of bits from the source.
     *
     * @param count The number of bits to read.
     * @return The bits read as a [ULong].
     */
    fun readBits(count: Int): ULong

    /**
     * Skip the specified number of bits in the source.
     *
     * @param count The number of bits to skip.
     */
    fun skipBits(count: Int)

    /**
     * Align the reader to the next byte boundary.
     */
    fun alignToNextByte()
}

private data class BitSourceImpl( // @formatter:off
    val source: Source,
    private val isSourceOwned: Boolean
) : BitSource { // @formatter:on
    companion object {
        private const val LAST_BIT: Int = Byte.SIZE_BITS - 1
    }

    private var isClosed: Boolean = false

    override var byte: Long = 0L
        private set
    override var bit: Int = 0
        private set
    override val exhausted: Boolean
        get() = source.exhausted() && bit == LAST_BIT

    private var currentByte: UByte = nextByte()

    private fun nextByte(): UByte = if (source.exhausted()) 0U.toUByte() else source.readUByte()

    private fun nextBit() {
        if (bit < LAST_BIT) {
            bit++
            return
        }
        currentByte = nextByte()
        bit = 0
        byte++
    }

    override fun readBits(count: Int): ULong {
        if (count == 0) return 0UL
        val lastBit = count - 1
        var result = 0UL
        for (bitIndex in 0..<count) {
            val bit = (currentByte.toULong() shr (LAST_BIT - bit)) and 0b1UL
            result = result or (bit shl (lastBit - bitIndex))
            nextBit()
        }
        return result
    }

    override fun skipBits(count: Int) {
        repeat(count) {
            nextBit()
        }
    }

    override fun alignToNextByte() {
        val count = Byte.SIZE_BITS - bit
        return skipBits(count)
    }

    override fun close() {
        if (isClosed) return
        if (isSourceOwned) source.close()
        byte = 0L
        bit = 0
        isClosed = true
    }
}

/**
 * Create a new [BitSource] from the current [Source].
 *
 * @param isSourceOwned Whether the [Source] is owned by the [BitSource] and should be closed when it is.
 * @return A new [BitSource] instance.
 */
fun Source.bitSource(
    isSourceOwned: Boolean = true
): BitSource = BitSourceImpl(this, isSourceOwned)