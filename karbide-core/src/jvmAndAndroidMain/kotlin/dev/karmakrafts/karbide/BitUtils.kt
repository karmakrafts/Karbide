@file:JvmName("BitUtils$") @file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package dev.karmakrafts.karbide

import java.lang.Integer as JInt
import java.lang.Long as JLong

actual fun Byte.reverseBits(count: Int): Byte = reverseBitsCommon(count)
actual fun Short.reverseBits(count: Int): Short = reverseBitsCommon(count)

actual fun Int.reverseBits(count: Int): Int {
    return if (count == Int.SIZE_BITS) JInt.reverse(this)
    else reverseBitsCommon(count)
}

actual fun Long.reverseBits(count: Int): Long {
    return if (count == Long.SIZE_BITS) JLong.reverse(this)
    else reverseBitsCommon(count)
}

actual fun UByte.reverseBits(count: Int): UByte = reverseBitsCommon(count)
actual fun UShort.reverseBits(count: Int): UShort = reverseBitsCommon(count)
actual fun UInt.reverseBits(count: Int): UInt = toInt().reverseBits(count).toUInt()
actual fun ULong.reverseBits(count: Int): ULong = toLong().reverseBits(count).toULong()