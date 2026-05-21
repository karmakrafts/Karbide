package dev.karmakrafts.karbide

/**
 * Read a single bit from the [BitSource].
 *
 * @return The bit read as a [UByte].
 */
fun BitSource.readBit(): UByte = readBits(1).toUByte()

/**
 * Read a nibble (4 bits) from the [BitSource].
 *
 * @return The nibble read as a [UByte].
 */
fun BitSource.readNibble(): UByte = readBits(4).toUByte()

/**
 * Read a [Byte] (8 bits) from the [BitSource].
 *
 * @return The byte read.
 */
fun BitSource.readByte(): Byte = readBits(Byte.SIZE_BITS).toByte()

/**
 * Read a [Short] (16 bits) from the [BitSource].
 *
 * @return The short read.
 */
fun BitSource.readShort(): Short = readBits(Short.SIZE_BITS).toShort()

/**
 * Read a [Short] (16 bits) from the [BitSource] in little endian byte order.
 *
 * @return The short read.
 */
fun BitSource.readShortLe(): Short = readBits(Short.SIZE_BITS).toShort().reverseBytes()

/**
 * Read an [Int] (32 bits) from the [BitSource].
 *
 * @return The integer read.
 */
fun BitSource.readInt(): Int = readBits(Int.SIZE_BITS).toInt()

/**
 * Read an [Int] (32 bits) from the [BitSource] in little endian byte order.
 *
 * @return The integer read.
 */
fun BitSource.readIntLe(): Int = readBits(Int.SIZE_BITS).toInt().reverseBytes()

/**
 * Read a [Long] (64 bits) from the [BitSource].
 *
 * @return The long read.
 */
fun BitSource.readLong(): Long = readBits(Long.SIZE_BITS).toLong()

/**
 * Read a [Long] (64 bits) from the [BitSource] in little endian byte order.
 *
 * @return The long read.
 */
fun BitSource.readLongLe(): Long = readBits(Long.SIZE_BITS).toLong().reverseBytes()

/**
 * Read a [Float] (32 bits) from the [BitSource].
 *
 * @return The float read.
 */
fun BitSource.readFloat(): Float = Float.fromBits(readBits(Float.SIZE_BITS).toInt())

/**
 * Read a [Float] (32 bits) from the [BitSource] in little endian byte order.
 *
 * @return The float read.
 */
fun BitSource.readFloatLe(): Float = Float.fromBits(readBits(Float.SIZE_BITS).toInt().reverseBytes())

/**
 * Read a [Double] (64 bits) from the [BitSource].
 *
 * @return The double read.
 */
fun BitSource.readDouble(): Double = Double.fromBits(readBits(Double.SIZE_BITS).toLong())

/**
 * Read a [Double] (64 bits) from the [BitSource] in little endian byte order.
 *
 * @return The double read.
 */
fun BitSource.readDoubleLe(): Double = Double.fromBits(readBits(Double.SIZE_BITS).toLong().reverseBytes())

/**
 * Read a [UByte] (8 bits) from the [BitSource].
 *
 * @return The unsigned byte read.
 */
fun BitSource.readUByte(): UByte = readBits(UByte.SIZE_BITS).toUByte()

/**
 * Read a [UShort] (16 bits) from the [BitSource].
 *
 * @return The unsigned short read.
 */
fun BitSource.readUShort(): UShort = readBits(UShort.SIZE_BITS).toUShort()

/**
 * Read a [UShort] (16 bits) from the [BitSource] in little endian byte order.
 *
 * @return The unsigned short read.
 */
fun BitSource.readUShortLe(): UShort = readBits(UShort.SIZE_BITS).toUShort().reverseBytes()

/**
 * Read a [UInt] (32 bits) from the [BitSource].
 *
 * @return The unsigned integer read.
 */
fun BitSource.readUInt(): UInt = readBits(UInt.SIZE_BITS).toUInt()

/**
 * Read a [UInt] (32 bits) from the [BitSource] in little endian byte order.
 *
 * @return The unsigned integer read.
 */
fun BitSource.readUIntLe(): UInt = readBits(UInt.SIZE_BITS).toUInt().reverseBytes()

/**
 * Read a [ULong] (64 bits) from the [BitSource].
 *
 * @return The unsigned long read.
 */
fun BitSource.readULong(): ULong = readBits(ULong.SIZE_BITS)

/**
 * Read a [ULong] (64 bits) from the [BitSource] in little endian byte order.
 *
 * @return The unsigned long read.
 */
fun BitSource.readULongLe(): ULong = readBits(ULong.SIZE_BITS).reverseBytes()

/**
 * Read a [ByteArray] of the specified size from the [BitSource].
 *
 * @param count The number of bytes to read.
 * @return The byte array read.
 */
fun BitSource.readByteArray(count: Int): ByteArray = ByteArray(count) {
    readByte()
}

/**
 * Skip a single bit in the [BitSource].
 */
fun BitSource.skipBit() = skipBits(1)

/**
 * Skip the specified number of nibbles (4 bits each) in the [BitSource].
 *
 * @param count The number of nibbles to skip.
 */
fun BitSource.skipNibbles(count: Int) = skipBits(count shl 2)

/**
 * Skip a nibble (4 bits) in the [BitSource].
 */
fun BitSource.skipNibble() = skipNibbles(1)

/**
 * Skip the specified number of bytes (8 bits each) in the [BitSource].
 *
 * @param count The number of bytes to skip.
 */
fun BitSource.skipBytes(count: Int) = skipBits(count shl 3)

/**
 * Skip a single byte in the [BitSource].
 */
fun BitSource.skipByte() = skipBytes(1)