package dev.karmakrafts.karbide

import kotlinx.io.Source
import kotlinx.io.readUByte
import kotlin.math.min

/**
 * Interface for reading individual bits from a source.
 */
interface BitSource : AutoCloseable {
    /**
     * The bit order of this bit source.
     */
    val bitOrder: BitOrder

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
    fun skipUntilNextByte()

    /**
     * Reset the internal state of this source.
     * This will reset [bit] and [byte] counters to 0.
     */
    fun reset()
}

private data class BitSourceImpl( // @formatter:off
    private val source: Source,
    private val isSourceOwned: Boolean,
    override val bitOrder: BitOrder
) : BitSource { // @formatter:on
    private var isClosed: Boolean = false

    override var byte: Long = 0L
        private set

    override val bit: Int get() = (bitsRead and 7).toInt()

    override val exhausted: Boolean
        get() = bitInBuffer == 0 && source.exhausted()

    private var bitsRead: Long = 0L
    private var bitInBuffer: Int = 0
    private var buffer: ULong = 0UL

    private fun fillBuffer() {
        while (bitInBuffer <= ULong.SIZE_BITS - Byte.SIZE_BITS && !source.exhausted()) {
            var byteValue = source.readUByte()
            if (bitOrder == BitOrder.MSB_FIRST) {
                byteValue = byteValue.reverseBits()
            }
            buffer = buffer or (byteValue.toULong() shl bitInBuffer)
            bitInBuffer += Byte.SIZE_BITS
        }
    }

    override fun readBits(count: Int): ULong {
        if (count == 0) return 0UL
        var remaining = count
        var result = 0UL
        while (remaining > 0) {
            fillBuffer()
            if (bitInBuffer == 0) break
            val take = min(remaining, bitInBuffer)
            val chunk = if (take == ULong.SIZE_BITS) buffer
            else {
                val mask = (1UL shl take) - 1UL
                buffer and mask
            }
            val reversedChunk = chunk.reverseBits(take)
            result = result or (reversedChunk shl (remaining - take))
            buffer = if (take == ULong.SIZE_BITS) 0UL else buffer shr take
            bitInBuffer -= take
            remaining -= take
            bitsRead += take
        }
        byte += bitsRead shr 3
        return result
    }

    override fun skipBits(count: Int) {
        var remaining = count
        while (remaining > 0) {
            fillBuffer()
            if (bitInBuffer == 0) break
            val take = min(remaining, bitInBuffer)
            buffer = if (take == ULong.SIZE_BITS) 0UL else buffer shr take
            bitInBuffer -= take
            remaining -= take
            bitsRead += take
        }
        byte += bitsRead shr 3
    }

    override fun skipUntilNextByte() {
        val count = (Byte.SIZE_BITS - bit) and 7
        if (count > 0) skipBits(count)
    }

    override fun reset() {
        if (isClosed) return
        buffer = 0UL
        bitInBuffer = 0
        bitsRead = 0L
        byte = 0
    }

    override fun close() {
        if (isClosed) return
        if (isSourceOwned) source.close()
        reset()
        isClosed = true
    }
}

/**
 * Create a new [BitSource] from the current [Source].
 *
 * @param isSourceOwned Whether the [Source] is owned by the [BitSource] and should be closed when it is.
 * @param bitOrder Whether bits are read in LSB or MSB first.
 * @return A new [BitSource] instance.
 */
fun Source.bitSource(
    isSourceOwned: Boolean = true, bitOrder: BitOrder = BitOrder.MSB_FIRST
): BitSource = BitSourceImpl(this, isSourceOwned, bitOrder)