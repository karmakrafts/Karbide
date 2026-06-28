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
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ByteArraySinkTest {

    @Test
    fun `Write to sink and convert to byte array`() {
        val sink = ByteArraySink()
        val buffer = Buffer()
        buffer.write(byteArrayOf(1, 2, 3, 4, 5))
        sink.write(buffer, 5)

        val result = sink.toByteArray()
        assertEquals(5, result.size)
        assertTrue(byteArrayOf(1, 2, 3, 4, 5).contentEquals(result))
    }

    @Test
    fun `Reset sink`() {
        val sink = ByteArraySink()
        val buffer = Buffer()
        buffer.write(byteArrayOf(1, 2, 3))
        sink.write(buffer, 3)
        sink.reset()

        assertEquals(0, sink.toByteArray().size)
    }

    @Test
    fun `Close sink`() {
        val sink = ByteArraySink()
        val buffer = Buffer()
        buffer.write(byteArrayOf(1, 2, 3))
        sink.write(buffer, 3)
        sink.close()

        assertEquals(0, sink.toByteArray().size)
    }

    @Test
    fun `Write multiple times`() {
        val sink = ByteArraySink()
        val buffer = Buffer()
        buffer.write(byteArrayOf(1, 2))
        sink.write(buffer, 2)
        buffer.write(byteArrayOf(3, 4))
        sink.write(buffer, 2)

        val result = sink.toByteArray()
        assertEquals(4, result.size)
        assertTrue(byteArrayOf(1, 2, 3, 4).contentEquals(result))
    }

    @Test
    fun `toByteArray consumes the buffer`() {
        val sink = ByteArraySink()
        val buffer = Buffer()
        buffer.write(byteArrayOf(1, 2, 3))
        sink.write(buffer, 3)

        val result1 = sink.toByteArray()
        assertEquals(3, result1.size)

        val result2 = sink.toByteArray()
        assertEquals(0, result2.size)
    }
}
