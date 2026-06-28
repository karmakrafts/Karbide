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
open class CommonLongReverseBytesBenchmark {
    private val random: Random = Random(System.now().epochSeconds)

    private fun Long.reverseBytes(): Long {
        val value = toULong()
        // @formatter:off
        return (((value and 0x00000000000000FFUL) shl 56) or
            ((value and 0x000000000000FF00UL) shl 40) or
            ((value and 0x0000000000FF0000UL) shl 24) or
            ((value and 0x00000000FF000000UL) shl  8) or
            ((value and 0x000000FF00000000UL) shr  8) or
            ((value and 0x0000FF0000000000UL) shr 24) or
            ((value and 0x00FF000000000000UL) shr 40) or
            ((value and 0xFF00000000000000UL) shr 56)).toLong()
        // @formatter:on
    }

    @JvmName("run")
    @Benchmark
    fun run(): Long = random.nextLong().reverseBytes()
}