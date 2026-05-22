package dev.karmakrafts.karbide

/**
 * Write the specified number of bits to the [BitSink] in least significant bit first order.
 *
 * @param count The number of bits to write.
 * @param bits The bits to write as a [ULong].
 */
fun BitSink.writeBitsLsb(count: Int, bits: ULong) = writeBits(count, bits.reverseBits(count))

/**
 * Write a [Byte] (8 bits) to the [BitSink] in LSB order.
 *
 * @param value The byte value to write.
 */
fun BitSink.writeByteLsb(value: Byte) = writeBitsLsb(Byte.SIZE_BITS, value.toULong())

/**
 * Write a [Short] (16 bits) to the [BitSink] in LSB order.
 *
 * @param value The short value to write.
 */
fun BitSink.writeShortLsb(value: Short) = writeBitsLsb(Short.SIZE_BITS, value.toULong())

/**
 * Write an [Int] (32 bits) to the [BitSink] in LSB order.
 *
 * @param value The integer value to write.
 */
fun BitSink.writeIntLsb(value: Int) = writeBitsLsb(Int.SIZE_BITS, value.toULong())

/**
 * Write a [Long] (64 bits) to the [BitSink] in LSB order.
 *
 * @param value The long value to write.
 */
fun BitSink.writeLongLsb(value: Long) = writeBitsLsb(Long.SIZE_BITS, value.toULong())

/**
 * Write a [Float] (32 bits) to the [BitSink] in LSB order.
 *
 * @param value The float value to write.
 */
fun BitSink.writeFloatLsb(value: Float) = writeIntLsb(value.toRawBits())

/**
 * Write a [Double] (64 bits) to the [BitSink] in LSB order.
 *
 * @param value The double value to write.
 */
fun BitSink.writeDoubleLsb(value: Double) = writeLongLsb(value.toRawBits())

/**
 * Write a [UByte] (8 bits) to the [BitSink] in LSB order.
 *
 * @param value The unsigned byte value to write.
 */
fun BitSink.writeUByteLsb(value: UByte) = writeBitsLsb(Byte.SIZE_BITS, value.toULong())

/**
 * Write a [UShort] (16 bits) to the [BitSink] in LSB order.
 *
 * @param value The unsigned short value to write.
 */
fun BitSink.writeUShortLsb(value: UShort) = writeBitsLsb(Short.SIZE_BITS, value.toULong())

/**
 * Write a [UInt] (32 bits) to the [BitSink] in LSB order.
 *
 * @param value The unsigned integer value to write.
 */
fun BitSink.writeUIntLsb(value: UInt) = writeBitsLsb(Int.SIZE_BITS, value.toULong())

/**
 * Write a [ULong] (64 bits) to the [BitSink] in LSB order.
 *
 * @param value The unsigned long value to write.
 */
fun BitSink.writeULongLsb(value: ULong) = writeBitsLsb(Long.SIZE_BITS, value)

/**
 * Write a [ByteArray] to the [BitSink] with each byte in LSB order.
 *
 * @param value The byte array to write.
 */
fun BitSink.writeByteArrayLsb(value: ByteArray) {
    for (byte in value) {
        writeByteLsb(byte)
    }
}

/**
 * Write a [String] to the [BitSink] in LSB order.
 *
 * @param value The string value to write.
 */
fun BitSink.writeStringLsb(value: String) = writeByteArrayLsb(value.encodeToByteArray())