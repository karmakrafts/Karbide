package dev.karmakrafts.karbide

// ------------------------------ Peeking ------------------------------

/**
 * Read a [Short] (16 bits) from the [BitSource] in little endian byte order
 * without consuming the bits.
 *
 * @return The short read.
 */
fun BitSource.peekShortLe(): Short = peekBits(Short.SIZE_BITS).toShort().reverseBytes()

/**
 * Read an [Int] (32 bits) from the [BitSource] in little endian byte order
 * without consuming the bits.
 *
 * @return The integer read.
 */
fun BitSource.peekIntLe(): Int = peekBits(Int.SIZE_BITS).toInt().reverseBytes()

/**
 * Read a [Long] (64 bits) from the [BitSource] in little endian byte order
 * without consuming the bits.
 *
 * @return The long read.
 */
fun BitSource.peekLongLe(): Long = peekBits(Long.SIZE_BITS).toLong().reverseBytes()

/**
 * Read a [Float] (32 bits) from the [BitSource] in little endian byte order
 * without consuming the bits.
 *
 * @return The float read.
 */
fun BitSource.peekFloatLe(): Float = Float.fromBits(peekBits(Float.SIZE_BITS).toInt().reverseBytes())

/**
 * Read a [Double] (64 bits) from the [BitSource] in little endian byte order
 * without consuming the bits.
 *
 * @return The double read.
 */
fun BitSource.peekDoubleLe(): Double = Double.fromBits(peekBits(Double.SIZE_BITS).toLong().reverseBytes())

/**
 * Read a [UShort] (16 bits) from the [BitSource] in little endian byte order
 * without consuming the bits.
 *
 * @return The unsigned short read.
 */
fun BitSource.peekUShortLe(): UShort = peekBits(UShort.SIZE_BITS).toUShort().reverseBytes()

/**
 * Read a [UInt] (32 bits) from the [BitSource] in little endian byte order
 * without consuming the bits.
 *
 * @return The unsigned integer read.
 */
fun BitSource.peekUIntLe(): UInt = peekBits(UInt.SIZE_BITS).toUInt().reverseBytes()

/**
 * Read a [ULong] (64 bits) from the [BitSource] in little endian byte order
 * without consuming the bits.
 *
 * @return The unsigned long read.
 */
fun BitSource.peekULongLe(): ULong = peekBits(ULong.SIZE_BITS).reverseBytes()

// ------------------------------ Reading ------------------------------

/**
 * Read a [Short] (16 bits) from the [BitSource] in little endian byte order.
 *
 * @return The short read.
 */
fun BitSource.readShortLe(): Short = readBits(Short.SIZE_BITS).toShort().reverseBytes()

/**
 * Read an [Int] (32 bits) from the [BitSource] in little endian byte order.
 *
 * @return The integer read.
 */
fun BitSource.readIntLe(): Int = readBits(Int.SIZE_BITS).toInt().reverseBytes()

/**
 * Read a [Long] (64 bits) from the [BitSource] in little endian byte order.
 *
 * @return The long read.
 */
fun BitSource.readLongLe(): Long = readBits(Long.SIZE_BITS).toLong().reverseBytes()

/**
 * Read a [Float] (32 bits) from the [BitSource] in little endian byte order.
 *
 * @return The float read.
 */
fun BitSource.readFloatLe(): Float = Float.fromBits(readBits(Float.SIZE_BITS).toInt().reverseBytes())

/**
 * Read a [Double] (64 bits) from the [BitSource] in little endian byte order.
 *
 * @return The double read.
 */
fun BitSource.readDoubleLe(): Double = Double.fromBits(readBits(Double.SIZE_BITS).toLong().reverseBytes())

/**
 * Read a [UShort] (16 bits) from the [BitSource] in little endian byte order.
 *
 * @return The unsigned short read.
 */
fun BitSource.readUShortLe(): UShort = readBits(UShort.SIZE_BITS).toUShort().reverseBytes()

/**
 * Read a [UInt] (32 bits) from the [BitSource] in little endian byte order.
 *
 * @return The unsigned integer read.
 */
fun BitSource.readUIntLe(): UInt = readBits(UInt.SIZE_BITS).toUInt().reverseBytes()

/**
 * Read a [ULong] (64 bits) from the [BitSource] in little endian byte order.
 *
 * @return The unsigned long read.
 */
fun BitSource.readULongLe(): ULong = readBits(ULong.SIZE_BITS).reverseBytes()