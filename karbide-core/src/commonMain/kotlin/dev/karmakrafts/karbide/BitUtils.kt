package dev.karmakrafts.karbide

/**
 * Reverses the bits of this [Byte].
 *
 * @return The [Byte] with its bits reversed.
 */
fun Byte.reverseBits(): Byte {
    var reversed = 0
    var value = toInt()
    repeat(Byte.SIZE_BITS) {
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
fun Short.reverseBits(): Short {
    var reversed = 0
    var value = toInt()
    repeat(Short.SIZE_BITS) {
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
fun Int.reverseBits(): Int {
    var reversed = 0
    var value = this
    repeat(Int.SIZE_BITS) {
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
fun Long.reverseBits(): Long {
    var reversed = 0L
    var value = this
    repeat(Long.SIZE_BITS) {
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
fun UByte.reverseBits(): UByte {
    var reversed = 0U
    var value = toUInt()
    repeat(UByte.SIZE_BITS) {
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
fun UShort.reverseBits(): UShort {
    var reversed = 0U
    var value = toUInt()
    repeat(UShort.SIZE_BITS) {
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
fun UInt.reverseBits(): UInt {
    var reversed = 0U
    var value = this
    repeat(UInt.SIZE_BITS) {
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
fun ULong.reverseBits(): ULong {
    var reversed = 0UL
    var value = this
    repeat(ULong.SIZE_BITS) {
        reversed = (reversed shl 1) or (value and 0b1UL)
        value = value shr 1
    }
    return reversed
}