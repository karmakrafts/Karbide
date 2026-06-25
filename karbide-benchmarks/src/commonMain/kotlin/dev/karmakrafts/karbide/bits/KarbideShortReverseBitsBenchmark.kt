package dev.karmakrafts.karbide.bits

import dev.karmakrafts.karbide.reverseBits
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock.System

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class KarbideShortReverseBitsBenchmark {
    private val random: Random = Random(System.now().epochSeconds)

    @JvmName("run")
    @Benchmark
    fun run(): Short = random.nextInt(Short.MAX_VALUE.toInt()).toShort().reverseBits()
}