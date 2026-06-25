package dev.karmakrafts.karbide

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class CommonShortReverseBytesBenchmark {
    private val random: Random = Random(Clock.System.now().epochSeconds)

    private fun Short.reverseBytes(): Short {
        val value = toUInt()
        return (((value shl 8) and 0xFF00U) or ((value shr 8) and 0x00FFU)).toShort()
    }

    @JvmName("run")
    @Benchmark
    fun run(): Short = random.nextInt(0, Short.MAX_VALUE.toInt()).toShort().reverseBytes()
}