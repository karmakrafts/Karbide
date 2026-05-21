package dev.karmakrafts.karbide

/**
 * Reverses the byte order of the [Short] value.
 *
 * @return The value with its bytes reversed.
 */
expect fun Short.reverseBytes(): Short

/**
 * Reverses the byte order of the [Int] value.
 *
 * @return The value with its bytes reversed.
 */
expect fun Int.reverseBytes(): Int

/**
 * Reverses the byte order of the [Long] value.
 *
 * @return The value with its bytes reversed.
 */
expect fun Long.reverseBytes(): Long

/**
 * Reverses the byte order of the [UShort] value.
 *
 * @return The value with its bytes reversed.
 */
fun UShort.reverseBytes(): UShort = toShort().reverseBytes().toUShort()

/**
 * Reverses the byte order of the [UInt] value.
 *
 * @return The value with its bytes reversed.
 */
fun UInt.reverseBytes(): UInt = toInt().reverseBytes().toUInt()

/**
 * Reverses the byte order of the [ULong] value.
 *
 * @return The value with its bytes reversed.
 */
fun ULong.reverseBytes(): ULong = toLong().reverseBytes().toULong()