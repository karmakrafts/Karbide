package dev.karmakrafts.karbide

@Suppress("NOTHING_TO_INLINE")
actual inline fun Byte.reverseBits(count: Int): Byte = reverseBitsCommon(count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun Short.reverseBits(count: Int): Short = reverseBitsCommon(count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun Int.reverseBits(count: Int): Int = reverseBitsCommon(count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun Long.reverseBits(count: Int): Long = reverseBitsCommon(count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun UByte.reverseBits(count: Int): UByte = reverseBitsCommon(count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun UShort.reverseBits(count: Int): UShort = reverseBitsCommon(count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun UInt.reverseBits(count: Int): UInt = reverseBitsCommon(count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun ULong.reverseBits(count: Int): ULong = reverseBitsCommon(count)