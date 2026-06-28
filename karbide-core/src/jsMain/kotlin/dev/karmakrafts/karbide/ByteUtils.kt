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

@JsFun(
    """
(x) => {
    const lo = x.low_1 | 0;
    const hi = x.high_1 | 0;
    return new x.constructor(
        ((hi & 0xFF) << 24) | ((hi & 0xFF00) << 8) | ((hi >>> 8) & 0xFF00) | ((hi >>> 24) & 0xFF),
        ((lo & 0xFF) << 24) | ((lo & 0xFF00) << 8) | ((lo >>> 8) & 0xFF00) | ((lo >>> 24) & 0xFF)
    );
}
"""
)
private external fun reverseBytesImpl(x: Long): Long

actual fun Long.reverseBytes(): Long = reverseBytesImpl(this)