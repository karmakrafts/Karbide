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

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Blackhole
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlinx.io.Buffer
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class CommonWriteSingleBitsBenchmark {
    private val random: Random = Random(Clock.System.now().epochSeconds)
    private val input: ByteArray = random.nextBytes(1024 * 1024) // 1MiB
    private val buffer: Buffer = Buffer()
    private var bitIndex: Int = 0

    @JvmName("run")
    @Benchmark
    fun run(blackHole: Blackhole) {
        buffer.clear()
        var currentByte = 0
        var currentBit = 0
        for (byte in input) {
            bitIndex = 0
            while (bitIndex < Byte.SIZE_BITS) {
                val bit = (byte.toInt() shr bitIndex) and 0b1
                currentByte = currentByte or (bit shl currentBit)
                currentBit++
                if (currentBit == Byte.SIZE_BITS) {
                    buffer.writeByte(currentByte.toByte())
                    currentByte = 0
                    currentBit = 0
                }
                bitIndex++
            }
        }
        if (currentBit > 0) {
            buffer.writeByte(currentByte.toByte())
        }
        blackHole.consume(buffer.size)
    }
}
