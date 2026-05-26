package dev.karmakrafts.karbide

// ------------------------------ Peeking ------------------------------

/**
 * Read a single bit from the [BitSource]
 * without consuming it.
 *
 * @return The bit read as a [UByte].
 */
fun BitSource.peekBit(): UByte = peekBits(1).toUByte()

/**
 * Read a nibble (4 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The nibble read as a [UByte].
 */
fun BitSource.peekNibble(): UByte = peekBits(4).toUByte()

/**
 * Read a [Byte] (8 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The byte read.
 */
fun BitSource.peekByte(): Byte = peekBits(Byte.SIZE_BITS).toByte()

/**
 * Read a [Short] (16 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The short read.
 */
fun BitSource.peekShort(): Short = peekBits(Short.SIZE_BITS).toShort()

/**
 * Read an [Int] (32 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The integer read.
 */
fun BitSource.peekInt(): Int = peekBits(Int.SIZE_BITS).toInt()

/**
 * Read a [Long] (64 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The long read.
 */
fun BitSource.peekLong(): Long = peekBits(Long.SIZE_BITS).toLong()

/**
 * Read a [Float] (32 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The float read.
 */
fun BitSource.peekFloat(): Float = Float.fromBits(peekBits(Float.SIZE_BITS).toInt())

/**
 * Read a [Double] (64 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The double read.
 */
fun BitSource.peekDouble(): Double = Double.fromBits(peekBits(Double.SIZE_BITS).toLong())

/**
 * Read a [UByte] (8 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The unsigned byte read.
 */
fun BitSource.peekUByte(): UByte = peekBits(UByte.SIZE_BITS).toUByte()

/**
 * Read a [UShort] (16 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The unsigned short read.
 */
fun BitSource.peekUShort(): UShort = peekBits(UShort.SIZE_BITS).toUShort()

/**
 * Read a [UInt] (32 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The unsigned integer read.
 */
fun BitSource.peekUInt(): UInt = peekBits(UInt.SIZE_BITS).toUInt()

/**
 * Read a [ULong] (64 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The unsigned long read.
 */
fun BitSource.peekULong(): ULong = peekBits(ULong.SIZE_BITS)

// ------------------------------ Reading ------------------------------

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
 * Read an [Int] (32 bits) from the [BitSource].
 *
 * @return The integer read.
 */
fun BitSource.readInt(): Int = readBits(Int.SIZE_BITS).toInt()

/**
 * Read a [Long] (64 bits) from the [BitSource].
 *
 * @return The long read.
 */
fun BitSource.readLong(): Long = readBits(Long.SIZE_BITS).toLong()

/**
 * Read a [Float] (32 bits) from the [BitSource].
 *
 * @return The float read.
 */
fun BitSource.readFloat(): Float = Float.fromBits(readBits(Float.SIZE_BITS).toInt())

/**
 * Read a [Double] (64 bits) from the [BitSource].
 *
 * @return The double read.
 */
fun BitSource.readDouble(): Double = Double.fromBits(readBits(Double.SIZE_BITS).toLong())

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
 * Read a [UInt] (32 bits) from the [BitSource].
 *
 * @return The unsigned integer read.
 */
fun BitSource.readUInt(): UInt = readBits(UInt.SIZE_BITS).toUInt()

/**
 * Read a [ULong] (64 bits) from the [BitSource].
 *
 * @return The unsigned long read.
 */
fun BitSource.readULong(): ULong = readBits(ULong.SIZE_BITS)

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
 * Read a [String] of the specified length from the [BitSource].
 *
 * @param length The number of characters to read.
 * @return The string read.
 */
fun BitSource.readString(length: Int): String = readByteArray(length).decodeToString()

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