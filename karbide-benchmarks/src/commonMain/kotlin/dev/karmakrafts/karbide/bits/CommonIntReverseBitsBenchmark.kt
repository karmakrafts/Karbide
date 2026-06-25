package dev.karmakrafts.karbide.bits

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock.System

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class CommonIntReverseBitsBenchmark {
    private val random: Random = Random(System.now().epochSeconds)

    private fun Int.reverseBits(count: Int = Int.SIZE_BITS): Int {
        var reversed = 0
        var value = this
        repeat(count) {
            reversed = (reversed shl 1) or (value and 0b1)
            value = value ushr 1
        }
        return reversed
    }

    @JvmName("run")
    @Benchmark
    fun run(): Int = random.nextInt().reverseBits()
}