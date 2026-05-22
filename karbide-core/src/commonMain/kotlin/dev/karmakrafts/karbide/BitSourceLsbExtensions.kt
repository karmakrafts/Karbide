package dev.karmakrafts.karbide

/**
 * Read the specified number of bits from the [BitSource] in least significant bit first order.
 *
 * @param count The number of bits to read.
 * @return The bits read as a [ULong].
 */
fun BitSource.readBitsLsb(count: Int): ULong = readBits(count).reverseBits(count)

/**
 * Read a [Byte] (8 bits) from the [BitSource] in LSB order.
 *
 * @return The byte read.
 */
fun BitSource.readByteLsb(): Byte = readBitsLsb(Byte.SIZE_BITS).toByte()

/**
 * Read a [Short] (16 bits) from the [BitSource] in LSB order.
 *
 * @return The short read.
 */
fun BitSource.readShortLsb(): Short = readBitsLsb(Short.SIZE_BITS).toShort()

/**
 * Read an [Int] (32 bits) from the [BitSource] in LSB order.
 *
 * @return The integer read.
 */
fun BitSource.readIntLsb(): Int = readBitsLsb(Int.SIZE_BITS).toInt()

/**
 * Read a [Long] (64 bits) from the [BitSource] in LSB order.
 *
 * @return The long read.
 */
fun BitSource.readLongLsb(): Long = readBitsLsb(Long.SIZE_BITS).toLong()

/**
 * Read a [Float] (32 bits) from the [BitSource] in LSB order.
 *
 * @return The float read.
 */
fun BitSource.readFloatLsb(): Float = Float.fromBits(readIntLsb())

/**
 * Read a [Double] (64 bits) from the [BitSource] in LSB order.
 *
 * @return The double read.
 */
fun BitSource.readDoubleLsb(): Double = Double.fromBits(readLongLsb())

/**
 * Read a [UByte] (8 bits) from the [BitSource] in LSB order.
 *
 * @return The unsigned byte read.
 */
fun BitSource.readUByteLsb(): UByte = readBitsLsb(UByte.SIZE_BITS).toUByte()

/**
 * Read a [UShort] (16 bits) from the [BitSource] in LSB order.
 *
 * @return The unsigned short read.
 */
fun BitSource.readUShortLsb(): UShort = readBitsLsb(UShort.SIZE_BITS).toUShort()

/**
 * Read a [UInt] (32 bits) from the [BitSource] in LSB order.
 *
 * @return The unsigned integer read.
 */
fun BitSource.readUIntLsb(): UInt = readBitsLsb(UInt.SIZE_BITS).toUInt()

/**
 * Read a [ULong] (64 bits) from the [BitSource] in LSB order.
 *
 * @return The unsigned long read.
 */
fun BitSource.readULongLsb(): ULong = readBitsLsb(ULong.SIZE_BITS)

/**
 * Read a [ByteArray] of the specified size from the [BitSource].
 *
 * @param count The number of bytes to read.
 * @return The byte array read.
 */
fun BitSource.readByteArrayLsb(count: Int): ByteArray = ByteArray(count) {
    readByteLsb()
}

/**
 * Read a [String] of the specified length from the [BitSource] in LSB order.
 *
 * @param length The number of characters to read.
 * @return The string read.
 */
fun BitSource.readStringLsb(length: Int): String = readByteArrayLsb(length).decodeToString()