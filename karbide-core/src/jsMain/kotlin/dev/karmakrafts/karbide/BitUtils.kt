@file:OptIn(ExperimentalWasmJsInterop::class)

package dev.karmakrafts.karbide

/**
 * These intrinsics are handwritten inline JS to force a more strict ASM.JS coercion.
 */

@JsFun(
    """
(x, count) => {
    x = x | 0;
    count = count | 0;
    x = ((x >>> 1) & 0x55) | ((x & 0x55) << 1);
    x = ((x >>> 2) & 0x33) | ((x & 0x33) << 2);
    x = ((x >>> 4) & 0x0F) | ((x & 0x0F) << 4);
    x = x >>> (8 - count);
    return (x << 24 >> 24) | 0;
}
"""
)
private external fun reverseBitsImpl(x: Byte, count: Int): Byte

actual fun Byte.reverseBits(count: Int): Byte = reverseBitsImpl(this, count)

@JsFun(
    """
(x, count) => {
    x = x | 0;
    count = count | 0;
    x = ((x >>> 1) & 0x5555) | ((x & 0x5555) << 1);
    x = ((x >>> 2) & 0x3333) | ((x & 0x3333) << 2);
    x = ((x >>> 4) & 0x0F0F) | ((x & 0x0F0F) << 4);
    x = ((x >>> 8) & 0x00FF) | ((x & 0x00FF) << 8);
    x = x >>> (16 - count);
    return (x << 16 >> 16) | 0;
}
"""
)
private external fun reverseBitsImpl(x: Short, count: Int): Short

actual fun Short.reverseBits(count: Int): Short = reverseBitsImpl(this, count)

@JsFun(
    """
(x, count) => {
    x = x | 0;
    count = count | 0;
    x = ((x >>> 1) & 0x55555555) | ((x & 0x55555555) << 1);
    x = ((x >>> 2) & 0x33333333) | ((x & 0x33333333) << 2);
    x = ((x >>> 4) & 0x0F0F0F0F) | ((x & 0x0F0F0F0F) << 4);
    x = ((x >>> 8) & 0x00FF00FF) | ((x & 0x00FF00FF) << 8);
    x = (x >>> 16) | (x << 16);
    return (x >>> (32 - count)) | 0;
}
"""
)
private external fun reverseBitsImpl(x: Int, count: Int): Int

actual fun Int.reverseBits(count: Int): Int = reverseBitsImpl(this, count)

actual fun Long.reverseBits(count: Int): Long {
    // TODO: Until BigInt support is stable in Kotlin/JS, we increase throughput by using two bitreverse32 calls
    val lo = toInt().reverseBits(Int.SIZE_BITS)
    val hi = (this ushr 32).toInt().reverseBits(Int.SIZE_BITS)
    return ((lo.toLong() shl 32) or (hi.toLong() and 0xFFFFFFFF)) ushr (Long.SIZE_BITS - count)
}

actual fun UByte.reverseBits(count: Int): UByte = toByte().reverseBits(count).toUByte()
actual fun UShort.reverseBits(count: Int): UShort = toShort().reverseBits(count).toUShort()
actual fun UInt.reverseBits(count: Int): UInt = toInt().reverseBits(count).toUInt()
actual fun ULong.reverseBits(count: Int): ULong = toLong().reverseBits(count).toULong()