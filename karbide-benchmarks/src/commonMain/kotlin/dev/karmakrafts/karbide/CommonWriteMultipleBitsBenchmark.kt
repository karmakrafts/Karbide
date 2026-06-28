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
import kotlin.math.min
import kotlin.random.Random
import kotlin.time.Clock.System

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class CommonWriteMultipleBitsBenchmark {
    private val random: Random = Random(System.now().epochSeconds)
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
                val toWrite = min(random.nextInt(Byte.SIZE_BITS), Byte.SIZE_BITS - bitIndex)
                val mask = ((1U shl toWrite) - 1U).toInt()
                var remaining = toWrite
                var bits = (byte.toInt() shr bitIndex) and mask
                while (remaining > 0) {
                    val space = Byte.SIZE_BITS - currentBit
                    val take = min(remaining, space)
                    val chunkMask = (1 shl take) - 1
                    val chunk = bits and chunkMask
                    currentByte = currentByte or (chunk shl currentBit)
                    currentBit += take
                    bits = bits shr take
                    remaining -= take
                    if (currentBit == Byte.SIZE_BITS) {
                        buffer.writeByte(currentByte.toByte())
                        currentByte = 0
                        currentBit = 0
                    }
                }
                bitIndex += toWrite
            }
        }
        if (currentBit > 0) {
            buffer.writeByte(currentByte.toByte())
        }
        blackHole.consume(buffer.size)
    }
}
