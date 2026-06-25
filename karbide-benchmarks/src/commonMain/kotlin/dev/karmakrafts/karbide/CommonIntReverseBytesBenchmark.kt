package dev.karmakrafts.karbide

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.jvm.JvmName
import kotlin.random.Random
import kotlin.time.Clock.System

@Suppress("UNUSED")
@State(Scope.Benchmark)
open class CommonIntReverseBytesBenchmark {
    private val random: Random = Random(System.now().epochSeconds)

    private fun Int.reverseBytes(): Int {
        val value = toUInt()
        // @formatter:off
        return (((value and 0x000000FFU) shl 24) or
            ((value and 0x0000FF00U) shl 8) or
            ((value and 0x00FF0000U) shr 8) or
            ((value and 0xFF000000U) shr 24)).toInt()
        // @formatter:on
    }

    @JvmName("run")
    @Benchmark
    fun run(): Int = random.nextInt().reverseBytes()
}