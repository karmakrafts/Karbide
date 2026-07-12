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
open class KarbideReadMultipleBitsBenchmark {
    private val random: Random = Random(System.now().epochSeconds)

    private val buffer: Buffer = Buffer().apply {
        write(random.nextBytes(1024 * 1024)) // 1MiB
    }

    @JvmName("run")
    @Benchmark
    fun run(blackHole: Blackhole) {
        buffer.peek().bitSource(false).use { source ->
            while (!source.exhausted) {
                val toRead = min(random.nextInt(Byte.SIZE_BITS), Byte.SIZE_BITS - source.bit)
                // Consume as a primitive to avoid boxing the ULong in the blackhole
                blackHole.consume(source.readBits(toRead).toLong())
            }
        }
    }
}