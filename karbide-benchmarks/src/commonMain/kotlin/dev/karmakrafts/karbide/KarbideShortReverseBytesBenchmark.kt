package dev.karmakrafts.karbide

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class KarbideShortReverseBytesBenchmark {
    private val random: Random = Random(Clock.System.now().epochSeconds)

    @JvmName("run")
    @Benchmark
    fun run(): Short = random.nextInt(0, Short.MAX_VALUE.toInt()).toShort().reverseBytes()
}