package dev.karmakrafts.karbide.bits

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock.System

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class CommonLongReverseBitsBenchmark {
    private val random: Random = Random(System.now().epochSeconds)

    private fun Long.reverseBits(count: Int = Long.SIZE_BITS): Long {
        var reversed = 0L
        var value = this
        repeat(count) {
            reversed = (reversed shl 1) or (value and 0b1L)
            value = value ushr 1
        }
        return reversed
    }

    @JvmName("run")
    @Benchmark
    fun run(): Long = random.nextLong().reverseBits()
}