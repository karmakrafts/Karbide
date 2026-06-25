@file:JvmName("BitUtils$") @file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package dev.karmakrafts.karbide

import java.lang.Integer as JInt
import java.lang.Long as JLong

actual fun Byte.reverseBits(count: Int): Byte = (JInt.reverse(toInt()) ushr (Int.SIZE_BITS - count)).toByte()
actual fun Short.reverseBits(count: Int): Short = (JInt.reverse(toInt()) ushr (Int.SIZE_BITS - count)).toShort()
actual fun Int.reverseBits(count: Int): Int = JInt.reverse(this) ushr (Int.SIZE_BITS - count)
actual fun Long.reverseBits(count: Int): Long = JLong.reverse(this) ushr (Long.SIZE_BITS - count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun UByte.reverseBits(count: Int): UByte = toByte().reverseBits(count).toUByte()

@Suppress("NOTHING_TO_INLINE")
actual inline fun UShort.reverseBits(count: Int): UShort = toShort().reverseBits(count).toUShort()

@Suppress("NOTHING_TO_INLINE")
actual inline fun UInt.reverseBits(count: Int): UInt = toInt().reverseBits(count).toUInt()

@Suppress("NOTHING_TO_INLINE")
actual inline fun ULong.reverseBits(count: Int): ULong = toLong().reverseBits(count).toULong()