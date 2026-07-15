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
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith

class BitSink32ExtensionsTest {
    @Test
    fun `Write zero to 32 bits across word boundaries`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            sink.writeBits32(0, UInt.MAX_VALUE)
            sink.writeBits32(4, 0xFAU)
            sink.writeBits32(32, 0xBCDEF012U)
            sink.writeBits32(4, 0x3U)
        }

        assertContentEquals(
            byteArrayOf(0xAB.toByte(), 0xCD.toByte(), 0xEF.toByte(), 0x01, 0x23), buffer.readByteArray()
        )
    }

    @Test
    fun `Write 32 bits in LSB first order`() {
        val buffer = Buffer()
        buffer.bitSink(bitOrder = BitOrder.LSB_FIRST).use { sink ->
            sink.writeBits32(32, 0x80402010U)
        }

        assertContentEquals(byteArrayOf(0x01, 0x02, 0x04, 0x08), buffer.readByteArray())
    }

    @Test
    fun `Write bits validates count without changing output`() {
        val buffer = Buffer()
        buffer.bitSink().use { sink ->
            assertFailsWith<IllegalArgumentException> { sink.writeBits32(-1, UInt.MAX_VALUE) }
            assertFailsWith<IllegalArgumentException> { sink.writeBits32(33, UInt.MAX_VALUE) }
            sink.writeBits32(8, 0xA5U)
        }

        assertContentEquals(byteArrayOf(0xA5.toByte()), buffer.readByteArray())
    }
}