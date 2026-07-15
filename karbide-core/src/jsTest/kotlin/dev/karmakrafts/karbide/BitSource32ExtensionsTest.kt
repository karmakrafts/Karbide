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
import kotlinx.io.EOFException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BitSource32ExtensionsTest {
    @Test
    fun `Read zero to 32 bits across word boundaries`() {
        val buffer = Buffer()
        buffer.write(byteArrayOf(0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte(), 0x01, 0x23))

        buffer.bitSource().use { source ->
            assertEquals(0U, source.readBits32(0))
            assertEquals(0xAU, source.readBits32(4))
            assertEquals(0xBCDEF012U, source.readBits32(32))
            assertEquals(0x3U, source.readBits32(4))
        }
    }

    @Test
    fun `Read 32 bits in LSB first order`() {
        val buffer = Buffer()
        buffer.write(byteArrayOf(0x01, 0x02, 0x04, 0x08))

        buffer.bitSource(bitOrder = BitOrder.LSB_FIRST).use { source ->
            assertEquals(0x80402010U, source.readBits32(32))
        }
    }

    @Test
    fun `Read bits validates count and preserves partial input on EOF`() {
        val buffer = Buffer()
        buffer.write(byteArrayOf(0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte()))

        buffer.bitSource().use { source ->
            assertFailsWith<IllegalArgumentException> { source.readBits32(-1) }
            assertFailsWith<IllegalArgumentException> { source.readBits32(33) }
            assertFailsWith<EOFException> { source.readBits32(32) }
            assertEquals(0xABCDEFU, source.readBits32(24))
        }
    }

    @Test
    fun `Peek zero to 32 bits without consuming across word boundaries`() {
        val buffer = Buffer()
        buffer.write(byteArrayOf(0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte(), 0x01, 0x23))

        buffer.bitSource().use { source ->
            assertEquals(0U, source.peekBits32(0))
            assertEquals(0xAU, source.peekBits32(4))
            assertEquals(0xAU, source.readBits32(4))
            assertEquals(0xBCDEF012U, source.peekBits32(32))
            assertEquals(0xBCDEF012U, source.peekBits32(32))
            assertEquals(0xBCDEF012U, source.readBits32(32))
            assertEquals(0x3U, source.peekBits32(4))
        }
    }

    @Test
    fun `Peek 32 bits in LSB first order without consuming`() {
        val buffer = Buffer()
        buffer.write(byteArrayOf(0x01, 0x02, 0x04, 0x08))

        buffer.bitSource(bitOrder = BitOrder.LSB_FIRST).use { source ->
            assertEquals(0x80402010U, source.peekBits32(32))
            assertEquals(0x80402010U, source.readBits32(32))
        }
    }

    @Test
    fun `Peek bits validates count and preserves partial input on EOF`() {
        val buffer = Buffer()
        buffer.write(byteArrayOf(0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte()))

        buffer.bitSource().use { source ->
            assertFailsWith<IllegalArgumentException> { source.peekBits32(-1) }
            assertFailsWith<IllegalArgumentException> { source.peekBits32(33) }
            assertFailsWith<EOFException> { source.peekBits32(32) }
            assertEquals(0xABCDEFU, source.readBits32(24))
        }
    }
}