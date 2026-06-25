package dev.karmakrafts.karbide

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock.System

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class KarbideIntReverseBytesBenchmark {
    private val random: Random = Random(System.now().epochSeconds)

    @JvmName("run")
    @Benchmark
    fun run(): Int = random.nextInt().reverseBytes()
}