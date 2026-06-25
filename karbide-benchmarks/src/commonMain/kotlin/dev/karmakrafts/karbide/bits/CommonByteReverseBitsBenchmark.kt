package dev.karmakrafts.karbide.bits

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock.System

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class CommonByteReverseBitsBenchmark {
    private val random: Random = Random(System.now().epochSeconds)

    private fun Byte.reverseBits(count: Int = Byte.SIZE_BITS): Byte {
        var reversed = 0
        var value = toInt()
        repeat(count) {
            reversed = (reversed shl 1) or (value and 0b1)
            value = value ushr 1
        }
        return reversed.toByte()
    }

    @JvmName("run")
    @Benchmark
    fun run(): Byte = random.nextInt(Byte.MAX_VALUE.toInt()).toByte().reverseBits()
}