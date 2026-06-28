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
import kotlinx.io.readByteArray
import kotlin.test.Test
import kotlin.test.assertEquals

class ByteArraySourceTest {
    @Test
    fun `Read from source`() {
        val data = byteArrayOf(1, 2, 3, 4, 5)
        val source = data.source()
        val buffer = Buffer()

        val read = source.readAtMostTo(buffer, 3)
        assertEquals(3, read)
        val result = buffer.readByteArray()
        assertEquals(3, result.size)
        assertEquals(1, result[0])
        assertEquals(2, result[1])
        assertEquals(3, result[2])
    }

    @Test
    fun `Read with offset and size`() {
        val data = byteArrayOf(0, 1, 2, 3, 4, 5, 6)
        val source = data.source(1, 4) // should read 1, 2, 3, 4
        val buffer = Buffer()

        val read = source.readAtMostTo(buffer, 10)
        assertEquals(4, read)
        val result = buffer.readByteArray()
        assertEquals(4, result.size)
        assertEquals(1, result[0])
        assertEquals(4, result[3])
    }

    @Test
    fun `Reset source`() {
        val data = byteArrayOf(1, 2, 3)
        val source = data.source()
        val buffer = Buffer()

        source.readAtMostTo(buffer, 2)
        source.reset()
        buffer.clear()

        val read = source.readAtMostTo(buffer, 3)
        assertEquals(3, read)
        val result = buffer.readByteArray()
        assertEquals(1, result[0])
        assertEquals(2, result[1])
        assertEquals(3, result[2])
    }

    @Test
    fun `EOF check`() {
        val data = byteArrayOf(1, 2)
        val source = data.source()
        val buffer = Buffer()

        assertEquals(2, source.readAtMostTo(buffer, 10))
        assertEquals(-1, source.readAtMostTo(buffer, 10))
    }

    @Test
    fun `Empty source`() {
        val data = byteArrayOf()
        val source = data.source()
        val buffer = Buffer()

        assertEquals(-1, source.readAtMostTo(buffer, 10))
    }
}
