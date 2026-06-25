@file:OptIn(ExperimentalWasmJsInterop::class)

package dev.karmakrafts.karbide

/**
 * These intrinsics are handwritten inline JS to force a more strict ASM.JS coercion.
 */

@JsFun(
    """
(x) => {
    x = x | 0;
    return (
        (((x & 0xFF) << 8) |
         ((x >>> 8) & 0xFF)) << 16 >> 16
    ) | 0;
}
"""
)
private external fun reverseBytesImpl(x: Short): Short

actual fun Short.reverseBytes(): Short = reverseBytesImpl(this)

@JsFun(
    """
(x) => {
    x = x | 0;
    return (
        ((x & 0xFF) << 24) |
        ((x & 0xFF00) << 8) |
        ((x >>> 8) & 0xFF00) |
        ((x >>> 24) & 0xFF)
    ) | 0;
}
"""
)
private external fun reverseBytesImpl(x: Int): Int

actual fun Int.reverseBytes(): Int = reverseBytesImpl(this)

actual fun Long.reverseBytes(): Long {
    // TODO: Until BigInt support is stable in Kotlin/JS, we double throughput by using two bswap32 calls
    val lo = toInt().reverseBytes()
    val hi = (this ushr 32).toInt().reverseBytes()
    return (lo.toLong() shl 32) or (hi.toLong() and 0xFFFFFFFF)
}