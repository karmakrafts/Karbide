package dev.karmakrafts.karbide

/**
 * Reverses the bits of this [Byte].
 *
 * @return The [Byte] with its bits reversed.
 */
fun Byte.reverseBits(count: Int = Byte.SIZE_BITS): Byte {
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
 * @return The [Short] with its bits reversed.
 */
fun Short.reverseBits(count: Int = Short.SIZE_BITS): Short {
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
 * @return The [Int] with its bits reversed.
 */
fun Int.reverseBits(count: Int = Int.SIZE_BITS): Int {
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
 * @return The [Long] with its bits reversed.
 */
fun Long.reverseBits(count: Int = Long.SIZE_BITS): Long {
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
 * @return The [UByte] with its bits reversed.
 */
fun UByte.reverseBits(count: Int = UByte.SIZE_BITS): UByte {
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
 * @return The [UShort] with its bits reversed.
 */
fun UShort.reverseBits(count: Int = UShort.SIZE_BITS): UShort {
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
 * @return The [UInt] with its bits reversed.
 */
fun UInt.reverseBits(count: Int = UInt.SIZE_BITS): UInt {
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
 * @return The [ULong] with its bits reversed.
 */
fun ULong.reverseBits(count: Int = ULong.SIZE_BITS): ULong {
    var reversed = 0UL
    var value = this
    repeat(count) {
        reversed = (reversed shl 1) or (value and 0b1UL)
        value = value shr 1
    }
    return reversed
}