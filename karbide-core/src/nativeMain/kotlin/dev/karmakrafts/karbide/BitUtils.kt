@file:OptIn(ExperimentalForeignApi::class)

package dev.karmakrafts.karbide

import dev.karmakrafts.karbide.builtins.karbide_bitreverse16
import dev.karmakrafts.karbide.builtins.karbide_bitreverse32
import dev.karmakrafts.karbide.builtins.karbide_bitreverse64
import dev.karmakrafts.karbide.builtins.karbide_bitreverse8
import kotlinx.cinterop.ExperimentalForeignApi

actual fun UByte.reverseBits(count: Int): UByte {
    return if (count == UByte.SIZE_BITS) karbide_bitreverse8(this)
    else reverseBitsCommon(count)
}

actual fun UShort.reverseBits(count: Int): UShort {
    return if (count == UShort.SIZE_BITS) karbide_bitreverse16(this)
    else reverseBitsCommon(count)
}

actual fun UInt.reverseBits(count: Int): UInt {
    return if (count == UInt.SIZE_BITS) karbide_bitreverse32(this)
    else reverseBitsCommon(count)
}

actual fun ULong.reverseBits(count: Int): ULong {
    return if (count == ULong.SIZE_BITS) karbide_bitreverse64(this)
    else reverseBitsCommon(count)
}

actual fun Byte.reverseBits(count: Int): Byte = toUByte().reverseBits(count).toByte()
actual fun Short.reverseBits(count: Int): Short = toUShort().reverseBits(count).toShort()
actual fun Int.reverseBits(count: Int): Int = toUInt().reverseBits(count).toInt()
actual fun Long.reverseBits(count: Int): Long = toULong().reverseBits(count).toLong()