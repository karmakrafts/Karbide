/*
 * Copyright 2026 Karma Krafts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.karmakrafts.karbide

import kotlinx.io.Sink

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

    /**
     * Reset the internal state of this sink.
     * This will reset [bit] and [byte] counters to 0.
     */
    fun reset()
}

/**
 * Create a new [BitSink] from the given [sink].
 *
 * @param sink The [Sink] the newly created [BitSink] will write to.
 * @param isSinkOwned Whether the [sink] is owned by the [BitSink] and should be closed when it is.
 * @param bitOrder Whether bits are written out LSB or MSB first.
 * @return A new [BitSink] instance.
 */
expect fun BitSink( // @formatter:off
    sink: Sink,
    isSinkOwned: Boolean = true,
    bitOrder: BitOrder = BitOrder.MSB_FIRST
): BitSink // @formatter:on

/**
 * Create a new [BitSink] from the current [Sink].
 *
 * @param isSinkOwned Whether the [Sink] is owned by the [BitSink] and should be closed when it is.
 * @param bitOrder Whether bits are written out LSB or MSB first.
 * @return A new [BitSink] instance.
 */
fun Sink.bitSink( // @formatter:off
    isSinkOwned: Boolean = true,
    bitOrder: BitOrder = BitOrder.MSB_FIRST
): BitSink = BitSink(this, isSinkOwned, bitOrder) // @formatter:on