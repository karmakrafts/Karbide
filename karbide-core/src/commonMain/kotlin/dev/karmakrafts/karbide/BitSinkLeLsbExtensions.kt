package dev.karmakrafts.karbide

/**
 * Write a [Short] (16 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The short value to write.
 */
fun BitSink.writeShortLeLsb(value: Short) = writeShortLsb(value.reverseBytes())

/**
 * Write an [Int] (32 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The integer value to write.
 */
fun BitSink.writeIntLeLsb(value: Int) = writeIntLsb(value.reverseBytes())

/**
 * Write a [Long] (64 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The long value to write.
 */
fun BitSink.writeLongLeLsb(value: Long) = writeLongLsb(value.reverseBytes())

/**
 * Write a [Float] (32 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The float value to write.
 */
fun BitSink.writeFloatLeLsb(value: Float) = writeIntLeLsb(value.toRawBits())

/**
 * Write a [Double] (64 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The double value to write.
 */
fun BitSink.writeDoubleLeLsb(value: Double) = writeLongLeLsb(value.toRawBits())

/**
 * Write a [UShort] (16 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The unsigned short value to write.
 */
fun BitSink.writeUShortLeLsb(value: UShort) = writeUShortLsb(value.reverseBytes())

/**
 * Write a [UInt] (32 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The unsigned integer value to write.
 */
fun BitSink.writeUIntLeLsb(value: UInt) = writeUIntLsb(value.reverseBytes())

/**
 * Write a [ULong] (64 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The unsigned long value to write.
 */
fun BitSink.writeULongLeLsb(value: ULong) = writeULongLsb(value.reverseBytes())