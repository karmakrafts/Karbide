package dev.karmakrafts.karbide

/**
 * Write a single bit to the [BitWriter].
 *
 * @param value The bit value to write (0 or 1).
 */
fun BitWriter.writeBit(value: UByte) = writeBits(1, value.toULong())

/**
 * Write a nibble (4 bits) to the [BitWriter].
 *
 * @param value The nibble value to write.
 */
fun BitWriter.writeNibble(value: UByte) = writeBits(4, value.toULong())

/**
 * Write a [Byte] (8 bits) to the [BitWriter].
 *
 * @param value The byte value to write.
 */
fun BitWriter.writeByte(value: Byte) = writeBits(Byte.SIZE_BITS, value.toULong())

/**
 * Write a [Short] (16 bits) to the [BitWriter].
 *
 * @param value The short value to write.
 */
fun BitWriter.writeShort(value: Short) = writeBits(Short.SIZE_BITS, value.toULong())

/**
 * Write a [Short] (16 bits) to the [BitWriter] in little endian byte order.
 *
 * @param value The short value to write.
 */
fun BitWriter.writeShortLe(value: Short) = writeBits(Short.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write an [Int] (32 bits) to the [BitWriter].
 *
 * @param value The integer value to write.
 */
fun BitWriter.writeInt(value: Int) = writeBits(Int.SIZE_BITS, value.toULong())

/**
 * Write an [Int] (32 bits) to the [BitWriter] in little endian byte order.
 *
 * @param value The integer value to write.
 */
fun BitWriter.writeIntLe(value: Int) = writeBits(Int.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write a [Long] (64 bits) to the [BitWriter].
 *
 * @param value The long value to write.
 */
fun BitWriter.writeLong(value: Long) = writeBits(Long.SIZE_BITS, value.toULong())

/**
 * Write a [Long] (64 bits) to the [BitWriter] in little endian byte order.
 *
 * @param value The long value to write.
 */
fun BitWriter.writeLongLe(value: Long) = writeBits(Long.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write a [Float] (32 bits) to the [BitWriter].
 *
 * @param value The float value to write.
 */
fun BitWriter.writeFloat(value: Float) = writeBits(Float.SIZE_BITS, value.toRawBits().toULong())

/**
 * Write a [Float] (32 bits) to the [BitWriter] in little endian byte order.
 *
 * @param value The float value to write.
 */
fun BitWriter.writeFloatLe(value: Float) = writeBits(Float.SIZE_BITS, value.toRawBits().reverseBytes().toULong())

/**
 * Write a [Double] (64 bits) to the [BitWriter].
 *
 * @param value The double value to write.
 */
fun BitWriter.writeDouble(value: Double) = writeBits(Double.SIZE_BITS, value.toRawBits().toULong())

/**
 * Write a [Double] (64 bits) to the [BitWriter] in little endian byte order.
 *
 * @param value The double value to write.
 */
fun BitWriter.writeDoubleLe(value: Double) = writeBits(Double.SIZE_BITS, value.toRawBits().reverseBytes().toULong())

/**
 * Write a [UByte] (8 bits) to the [BitWriter].
 *
 * @param value The unsigned byte value to write.
 */
fun BitWriter.writeUByte(value: UByte) = writeBits(UByte.SIZE_BITS, value.toULong())

/**
 * Write a [UShort] (16 bits) to the [BitWriter].
 *
 * @param value The unsigned short value to write.
 */
fun BitWriter.writeUShort(value: UShort) = writeBits(UShort.SIZE_BITS, value.toULong())

/**
 * Write a [UShort] (16 bits) to the [BitWriter] in little endian byte order.
 *
 * @param value The unsigned short value to write.
 */
fun BitWriter.writeUShortLe(value: UShort) = writeBits(UShort.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write a [UInt] (32 bits) to the [BitWriter].
 *
 * @param value The unsigned integer value to write.
 */
fun BitWriter.writeUInt(value: UInt) = writeBits(UInt.SIZE_BITS, value.toULong())

/**
 * Write a [UInt] (32 bits) to the [BitWriter] in little endian byte order.
 *
 * @param value The unsigned integer value to write.
 */
fun BitWriter.writeUIntLe(value: UInt) = writeBits(UInt.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write a [ULong] (64 bits) to the [BitWriter].
 *
 * @param value The unsigned long value to write.
 */
fun BitWriter.writeULong(value: ULong) = writeBits(ULong.SIZE_BITS, value)

/**
 * Write a [ULong] (64 bits) to the [BitWriter] in little endian byte order.
 *
 * @param value The unsigned long value to write.
 */
fun BitWriter.writeULongLe(value: ULong) = writeBits(ULong.SIZE_BITS, value.reverseBytes())

/**
 * Write a [ByteArray] to the [BitWriter].
 *
 * @param value The byte array to write.
 */
fun BitWriter.writeByteArray(value: ByteArray) {
    for (byte in value) {
        writeByte(byte)
    }
}

/**
 * Write a single padding bit to the [BitWriter].
 *
 * @param value The bit value to write (0 or 1).
 */
fun BitWriter.padBit(value: UByte) = padBits(1, value)

/**
 * Write the specified number of padding nibbles (4 bits each) to the [BitWriter].
 *
 * @param count The number of nibbles to write.
 * @param value The bit value to write (0 or 1).
 */
fun BitWriter.padNibbles(count: Int, value: UByte) = padBits(count shl 2, value)

/**
 * Write a single padding nibble (4 bits) to the [BitWriter].
 *
 * @param value The bit value to write (0 or 1).
 */
fun BitWriter.padNibble(value: UByte) = padNibbles(1, value)

/**
 * Write the specified number of padding bytes (8 bits each) to the [BitWriter].
 *
 * @param count The number of bytes to write.
 * @param value The bit value to write (0 or 1).
 */
fun BitWriter.padBytes(count: Int, value: UByte) = padBits(count shl 3, value)

/**
 * Write a single padding byte to the [BitWriter].
 *
 * @param value The bit value to write (0 or 1).
 */
fun BitWriter.padByte(value: UByte) = padBytes(1, value)