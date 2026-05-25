package dev.karmakrafts.karbide

/**
 * Reverses the bits of this [Byte].
 *
 * @param count The number of bits to reverse.
 * @return The [Byte] with its bits reversed.
 */
expect fun Byte.reverseBits(count: Int = Byte.SIZE_BITS): Byte

internal fun Byte.reverseBitsCommon(count: Int = Byte.SIZE_BITS): Byte {
    var reversed = 0
    var value = toInt()
    repeat(count) {
        reversed = (reversed shl 1) or (value and 0b1)
        value = value ushr 1
    }
    return reversed.toByte()
}

/**
 * Reverses the bits of this [Short].
 *
 * @param count The number of bits to reverse.
 * @return The [Short] with its bits reversed.
 */
expect fun Short.reverseBits(count: Int = Short.SIZE_BITS): Short

internal fun Short.reverseBitsCommon(count: Int = Short.SIZE_BITS): Short {
    var reversed = 0
    var value = toInt()
    repeat(count) {
        reversed = (reversed shl 1) or (value and 0b1)
        value = value ushr 1
    }
    return reversed.toShort()
}

/**
 * Reverses the bits of this [Int].
 *
 * @param count The number of bits to reverse.
 * @return The [Int] with its bits reversed.
 */
expect fun Int.reverseBits(count: Int = Int.SIZE_BITS): Int

internal fun Int.reverseBitsCommon(count: Int = Int.SIZE_BITS): Int {
    var reversed = 0
    var value = this
    repeat(count) {
        reversed = (reversed shl 1) or (value and 0b1)
        value = value ushr 1
    }
    return reversed
}

/**
 * Reverses the bits of this [Long].
 *
 * @param count The number of bits to reverse.
 * @return The [Long] with its bits reversed.
 */
expect fun Long.reverseBits(count: Int = Long.SIZE_BITS): Long

internal fun Long.reverseBitsCommon(count: Int = Long.SIZE_BITS): Long {
    var reversed = 0L
    var value = this
    repeat(count) {
        reversed = (reversed shl 1) or (value and 0b1L)
        value = value ushr 1
    }
    return reversed
}

/**
 * Reverses the bits of this [UByte].
 *
 * @param count The number of bits to reverse.
 * @return The [UByte] with its bits reversed.
 */
expect fun UByte.reverseBits(count: Int = UByte.SIZE_BITS): UByte

internal fun UByte.reverseBitsCommon(count: Int = UByte.SIZE_BITS): UByte {
    var reversed = 0U
    var value = toUInt()
    repeat(count) {
        reversed = (reversed shl 1) or (value and 0b1U)
        value = value shr 1
    }
    return reversed.toUByte()
}

/**
 * Reverses the bits of this [UShort].
 *
 * @param count The number of bits to reverse.
 * @return The [UShort] with its bits reversed.
 */
expect fun UShort.reverseBits(count: Int = UShort.SIZE_BITS): UShort

internal fun UShort.reverseBitsCommon(count: Int = UShort.SIZE_BITS): UShort {
    var reversed = 0U
    var value = toUInt()
    repeat(count) {
        reversed = (reversed shl 1) or (value and 0b1U)
        value = value shr 1
    }
    return reversed.toUShort()
}

/**
 * Reverses the bits of this [UInt].
 *
 * @param count The number of bits to reverse.
 * @return The [UInt] with its bits reversed.
 */
expect fun UInt.reverseBits(count: Int = UInt.SIZE_BITS): UInt

internal fun UInt.reverseBitsCommon(count: Int = UInt.SIZE_BITS): UInt {
    var reversed = 0U
    var value = this
    repeat(count) {
        reversed = (reversed shl 1) or (value and 0b1U)
        value = value shr 1
    }
    return reversed
}

/**
 * Reverses the bits of this [ULong].
 *
 * @param count The number of bits to reverse.
 * @return The [ULong] with its bits reversed.
 */
expect fun ULong.reverseBits(count: Int = ULong.SIZE_BITS): ULong

internal fun ULong.reverseBitsCommon(count: Int = ULong.SIZE_BITS): ULong {
    var reversed = 0UL
    var value = this
    repeat(count) {
        reversed = (reversed shl 1) or (value and 0b1UL)
        value = value shr 1
    }
    return reversed
}