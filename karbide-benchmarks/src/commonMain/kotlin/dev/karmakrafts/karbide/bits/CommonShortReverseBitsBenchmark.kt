package dev.karmakrafts.karbide.bits

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock.System

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class CommonShortReverseBitsBenchmark {
    private val random: Random = Random(System.now().epochSeconds)

    private fun Short.reverseBits(count: Int = Short.SIZE_BITS): Short {
        var reversed = 0
        var value = toInt()
        repeat(count) {
            reversed = (reversed shl 1) or (value and 0b1)
            value = value ushr 1
        }
        return reversed.toShort()
    }

    @JvmName("run")
    @Benchmark
    fun run(): Short = random.nextInt(Short.MAX_VALUE.toInt()).toShort().reverseBits()
}