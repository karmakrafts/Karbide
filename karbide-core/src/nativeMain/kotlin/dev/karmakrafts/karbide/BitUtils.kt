@file:OptIn(ExperimentalForeignApi::class)

package dev.karmakrafts.karbide

import dev.karmakrafts.karbide.builtins.karbide_bitreverse16
import dev.karmakrafts.karbide.builtins.karbide_bitreverse32
import dev.karmakrafts.karbide.builtins.karbide_bitreverse64
import dev.karmakrafts.karbide.builtins.karbide_bitreverse8
import kotlinx.cinterop.ExperimentalForeignApi

actual fun UByte.reverseBits(count: Int): UByte =
    (karbide_bitreverse8(this).toUInt() shr (UByte.SIZE_BITS - count)).toUByte()

actual fun UShort.reverseBits(count: Int): UShort =
    (karbide_bitreverse16(this).toUInt() shr (UShort.SIZE_BITS - count)).toUShort()

actual fun UInt.reverseBits(count: Int): UInt = karbide_bitreverse32(this) shr (UInt.SIZE_BITS - count)
actual fun ULong.reverseBits(count: Int): ULong = karbide_bitreverse64(this) shr (ULong.SIZE_BITS - count)

actual fun Byte.reverseBits(count: Int): Byte = toUByte().reverseBits(count).toByte()
actual fun Short.reverseBits(count: Int): Short = toUShort().reverseBits(count).toShort()
actual fun Int.reverseBits(count: Int): Int = toUInt().reverseBits(count).toInt()
actual fun Long.reverseBits(count: Int): Long = toULong().reverseBits(count).toLong()