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

package dev.karmakrafts.karbide.bytes

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock.System

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class CommonShortReverseBytesBenchmark {
    private val random: Random = Random(System.now().epochSeconds)

    private fun Short.reverseBytes(): Short {
        val value = toUInt()
        return (((value shl 8) and 0xFF00U) or ((value shr 8) and 0x00FFU)).toShort()
    }

    @JvmName("run")
    @Benchmark
    fun run(): Short = random.nextInt(0, Short.MAX_VALUE.toInt()).toShort().reverseBytes()
}