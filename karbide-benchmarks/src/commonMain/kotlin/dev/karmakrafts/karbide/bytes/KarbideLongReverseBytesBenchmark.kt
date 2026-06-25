package dev.karmakrafts.karbide.bytes

import dev.karmakrafts.karbide.reverseBytes
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock.System

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class KarbideLongReverseBytesBenchmark {
    private val random: Random = Random(System.now().epochSeconds)

    @JvmName("run")
    @Benchmark
    fun run(): Long = random.nextLong().reverseBytes()
}