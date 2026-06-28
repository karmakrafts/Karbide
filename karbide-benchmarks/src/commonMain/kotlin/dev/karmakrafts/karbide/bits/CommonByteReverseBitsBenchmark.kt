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

package dev.karmakrafts.karbide.bits

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock.System

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class CommonByteReverseBitsBenchmark {
    private val random: Random = Random(System.now().epochSeconds)

    private fun Byte.reverseBits(count: Int = Byte.SIZE_BITS): Byte {
        var reversed = 0
        var value = toInt()
        repeat(count) {
            reversed = (reversed shl 1) or (value and 0b1)
            value = value ushr 1
        }
        return reversed.toByte()
    }

    @JvmName("run")
    @Benchmark
    fun run(): Byte = random.nextInt(Byte.MAX_VALUE.toInt()).toByte().reverseBits()
}