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

import kotlinx.io.EOFException
import kotlinx.io.Source

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
     *
     * This is only true when the internal bit buffer is empty and the underlying source is exhausted. Use
     * [requestBits] or [requireBits] to check whether a specific number of bits can be read.
     */
    val exhausted: Boolean

    /**
     * Request that at least [count] bits are available without consuming them.
     *
     * @param count The number of bits that should be available.
     * @return `true` if at least [count] bits can be read, `false` otherwise.
     */
    fun requestBits(count: Int): Boolean

    /**
     * Require that at least [count] bits are available without consuming them.
     *
     * @param count The number of bits that should be available.
     * @throws EOFException If fewer than [count] bits are available.
     */
    fun requireBits(count: Int)

    /**
     * Read the specified number of bits without consuming them.
     *
     * @param count The number of bits to peek.
     * @return The bits read as a [ULong].
     */
    fun peekBits(count: Int): ULong

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

/**
 * Create a new [BitSource] from the given [source].
 *
 * @param source The [Source] the newly created [BitSource] reads from.
 * @param isSourceOwned Whether the [source] is owned by the [BitSource] and should be closed when it is.
 * @param bitOrder Whether bits are read in LSB or MSB first.
 * @return A new [BitSource] instance.
 */
expect fun BitSource( // @formatter:off
    source: Source,
    isSourceOwned: Boolean = true,
    bitOrder: BitOrder = BitOrder.MSB_FIRST
): BitSource // @formatter:on

/**
 * Create a new [BitSource] from the current [Source].
 *
 * @param isSourceOwned Whether the [Source] is owned by the [BitSource] and should be closed when it is.
 * @param bitOrder Whether bits are read in LSB or MSB first.
 * @return A new [BitSource] instance.
 */
fun Source.bitSource( // @formatter:off
    isSourceOwned: Boolean = true,
    bitOrder: BitOrder = BitOrder.MSB_FIRST
): BitSource = BitSource(this, isSourceOwned, bitOrder) // @formatter:on