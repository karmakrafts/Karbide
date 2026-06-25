package dev.karmakrafts.karbide

actual fun Byte.reverseBits(count: Int): Byte = reverseBitsCommon(count)
actual fun Short.reverseBits(count: Int): Short = reverseBitsCommon(count)
actual fun Int.reverseBits(count: Int): Int = reverseBitsCommon(count)
actual fun Long.reverseBits(count: Int): Long = reverseBitsCommon(count)

actual fun UByte.reverseBits(count: Int): UByte = reverseBitsCommon(count)
actual fun UShort.reverseBits(count: Int): UShort = reverseBitsCommon(count)
actual fun UInt.reverseBits(count: Int): UInt = reverseBitsCommon(count)
actual fun ULong.reverseBits(count: Int): ULong = reverseBitsCommon(count)