package dev.karmakrafts.karbide

/**
 * Write a [Short] (16 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The short value to write.
 */
fun BitSink.writeShortLe(value: Short) = writeBits(Short.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write an [Int] (32 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The integer value to write.
 */
fun BitSink.writeIntLe(value: Int) = writeBits(Int.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write a [Long] (64 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The long value to write.
 */
fun BitSink.writeLongLe(value: Long) = writeBits(Long.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write a [Float] (32 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The float value to write.
 */
fun BitSink.writeFloatLe(value: Float) = writeBits(Float.SIZE_BITS, value.toRawBits().reverseBytes().toULong())

/**
 * Write a [Double] (64 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The double value to write.
 */
fun BitSink.writeDoubleLe(value: Double) = writeBits(Double.SIZE_BITS, value.toRawBits().reverseBytes().toULong())

/**
 * Write a [UShort] (16 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The unsigned short value to write.
 */
fun BitSink.writeUShortLe(value: UShort) = writeBits(UShort.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write a [UInt] (32 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The unsigned integer value to write.
 */
fun BitSink.writeUIntLe(value: UInt) = writeBits(UInt.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write a [ULong] (64 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The unsigned long value to write.
 */
fun BitSink.writeULongLe(value: ULong) = writeBits(ULong.SIZE_BITS, value.reverseBytes())