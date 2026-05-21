package dev.karmakrafts.karbide

/**
 * Read a single bit from the [BitReader].
 *
 * @return The bit read as a [UByte].
 */
fun BitReader.readBit(): UByte = readBits(1).toUByte()

/**
 * Read a nibble (4 bits) from the [BitReader].
 *
 * @return The nibble read as a [UByte].
 */
fun BitReader.readNibble(): UByte = readBits(4).toUByte()

/**
 * Read a [Byte] (8 bits) from the [BitReader].
 *
 * @return The byte read.
 */
fun BitReader.readByte(): Byte = readBits(Byte.SIZE_BITS).toByte()

/**
 * Read a [Short] (16 bits) from the [BitReader].
 *
 * @return The short read.
 */
fun BitReader.readShort(): Short = readBits(Short.SIZE_BITS).toShort()

/**
 * Read a [Short] (16 bits) from the [BitReader] in little endian byte order.
 *
 * @return The short read.
 */
fun BitReader.readShortLe(): Short = readBits(Short.SIZE_BITS).toShort().reverseBytes()

/**
 * Read an [Int] (32 bits) from the [BitReader].
 *
 * @return The integer read.
 */
fun BitReader.readInt(): Int = readBits(Int.SIZE_BITS).toInt()

/**
 * Read an [Int] (32 bits) from the [BitReader] in little endian byte order.
 *
 * @return The integer read.
 */
fun BitReader.readIntLe(): Int = readBits(Int.SIZE_BITS).toInt().reverseBytes()

/**
 * Read a [Long] (64 bits) from the [BitReader].
 *
 * @return The long read.
 */
fun BitReader.readLong(): Long = readBits(Long.SIZE_BITS).toLong()

/**
 * Read a [Long] (64 bits) from the [BitReader] in little endian byte order.
 *
 * @return The long read.
 */
fun BitReader.readLongLe(): Long = readBits(Long.SIZE_BITS).toLong().reverseBytes()

/**
 * Read a [Float] (32 bits) from the [BitReader].
 *
 * @return The float read.
 */
fun BitReader.readFloat(): Float = Float.fromBits(readBits(Float.SIZE_BITS).toInt())

/**
 * Read a [Float] (32 bits) from the [BitReader] in little endian byte order.
 *
 * @return The float read.
 */
fun BitReader.readFloatLe(): Float = Float.fromBits(readBits(Float.SIZE_BITS).toInt().reverseBytes())

/**
 * Read a [Double] (64 bits) from the [BitReader].
 *
 * @return The double read.
 */
fun BitReader.readDouble(): Double = Double.fromBits(readBits(Double.SIZE_BITS).toLong())

/**
 * Read a [Double] (64 bits) from the [BitReader] in little endian byte order.
 *
 * @return The double read.
 */
fun BitReader.readDoubleLe(): Double = Double.fromBits(readBits(Double.SIZE_BITS).toLong().reverseBytes())

/**
 * Read a [UByte] (8 bits) from the [BitReader].
 *
 * @return The unsigned byte read.
 */
fun BitReader.readUByte(): UByte = readBits(UByte.SIZE_BITS).toUByte()

/**
 * Read a [UShort] (16 bits) from the [BitReader].
 *
 * @return The unsigned short read.
 */
fun BitReader.readUShort(): UShort = readBits(UShort.SIZE_BITS).toUShort()

/**
 * Read a [UShort] (16 bits) from the [BitReader] in little endian byte order.
 *
 * @return The unsigned short read.
 */
fun BitReader.readUShortLe(): UShort = readBits(UShort.SIZE_BITS).toUShort().reverseBytes()

/**
 * Read a [UInt] (32 bits) from the [BitReader].
 *
 * @return The unsigned integer read.
 */
fun BitReader.readUInt(): UInt = readBits(UInt.SIZE_BITS).toUInt()

/**
 * Read a [UInt] (32 bits) from the [BitReader] in little endian byte order.
 *
 * @return The unsigned integer read.
 */
fun BitReader.readUIntLe(): UInt = readBits(UInt.SIZE_BITS).toUInt().reverseBytes()

/**
 * Read a [ULong] (64 bits) from the [BitReader].
 *
 * @return The unsigned long read.
 */
fun BitReader.readULong(): ULong = readBits(ULong.SIZE_BITS)

/**
 * Read a [ULong] (64 bits) from the [BitReader] in little endian byte order.
 *
 * @return The unsigned long read.
 */
fun BitReader.readULongLe(): ULong = readBits(ULong.SIZE_BITS).reverseBytes()

/**
 * Read a [ByteArray] of the specified size from the [BitReader].
 *
 * @param count The number of bytes to read.
 * @return The byte array read.
 */
fun BitReader.readByteArray(count: Int): ByteArray = ByteArray(count) {
    readByte()
}

/**
 * Skip a single bit in the [BitReader].
 */
fun BitReader.skipBit() = skipBits(1)

/**
 * Skip the specified number of nibbles (4 bits each) in the [BitReader].
 *
 * @param count The number of nibbles to skip.
 */
fun BitReader.skipNibbles(count: Int) = skipBits(count shl 2)

/**
 * Skip a nibble (4 bits) in the [BitReader].
 */
fun BitReader.skipNibble() = skipNibbles(1)

/**
 * Skip the specified number of bytes (8 bits each) in the [BitReader].
 *
 * @param count The number of bytes to skip.
 */
fun BitReader.skipBytes(count: Int) = skipBits(count shl 3)

/**
 * Skip a single byte in the [BitReader].
 */
fun BitReader.skipByte() = skipBytes(1)