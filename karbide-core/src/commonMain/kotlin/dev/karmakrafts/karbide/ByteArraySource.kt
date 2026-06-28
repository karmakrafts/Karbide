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

import kotlinx.io.Buffer
import kotlinx.io.RawSource
import kotlin.math.min

/**
 * A [RawSource] implementation that reads from a [ByteArray].
 *
 * @property array The byte array to read from.
 * @property offset The offset in the array to start reading from.
 * @property size The number of bytes to read from the array.
 */
class ByteArraySource internal constructor( // @formatter:off
    var array: ByteArray,
    var offset: Int,
    var size: Int
) : RawSource { // @formatter:on
    private var totalOffset: Int = 0

    /**
     * Read at most [byteCount] bytes from the source into [sink].
     *
     * @param sink The buffer to read into.
     * @param byteCount The maximum number of bytes to read.
     * @return The number of bytes read, or -1 if the end of the source has been reached.
     */
    override fun readAtMostTo(sink: Buffer, byteCount: Long): Long {
        if (totalOffset >= size) return -1L // Reached EOF
        val toWrite = min((size - totalOffset).toLong(), byteCount).toInt()
        val start = offset + totalOffset
        val end = start + toWrite
        sink.write(array, start, end)
        totalOffset += toWrite
        return toWrite.toLong()
    }

    /**
     * Reset the source to the beginning.
     */
    fun reset() {
        totalOffset = 0
    }

    /**
     * Close the source.
     */
    override fun close() = Unit
}

/**
 * Create a [ByteArraySource] from this [ByteArray].
 *
 * @param offset The offset in the array to start reading from.
 * @param size The number of bytes to read from the array.
 * @return A new [ByteArraySource] instance.
 */
fun ByteArray.source( // @formatter:off
    offset: Int = 0,
    size: Int = this@source.size
): ByteArraySource = ByteArraySource(this, offset, size) // @formatter:on