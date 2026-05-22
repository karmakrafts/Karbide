package dev.karmakrafts.karbide

/**
 * Read a [Short] (16 bits) from the [BitSource] in little endian and LSB order.
 *
 * @return The short read.
 */
fun BitSource.readShortLeLsb(): Short = readShortLsb().reverseBytes()

/**
 * Read an [Int] (32 bits) from the [BitSource] in little endian and LSB order.
 *
 * @return The integer read.
 */
fun BitSource.readIntLeLsb(): Int = readIntLsb().reverseBytes()

/**
 * Read a [Long] (64 bits) from the [BitSource] in little endian and LSB order.
 *
 * @return The long read.
 */
fun BitSource.readLongLeLsb(): Long = readLongLsb().reverseBytes()

/**
 * Read a [Float] (32 bits) from the [BitSource] in little endian and LSB order.
 *
 * @return The float read.
 */
fun BitSource.readFloatLeLsb(): Float = Float.fromBits(readIntLeLsb())

/**
 * Read a [Double] (64 bits) from the [BitSource] in little endian and LSB order.
 *
 * @return The double read.
 */
fun BitSource.readDoubleLeLsb(): Double = Double.fromBits(readLongLeLsb())

/**
 * Read a [UShort] (16 bits) from the [BitSource] in little endian and LSB order.
 *
 * @return The unsigned short read.
 */
fun BitSource.readUShortLeLsb(): UShort = readUShortLsb().reverseBytes()

/**
 * Read a [UInt] (32 bits) from the [BitSource] in little endian and LSB order.
 *
 * @return The unsigned integer read.
 */
fun BitSource.readUIntLeLsb(): UInt = readUIntLsb().reverseBytes()

/**
 * Read a [ULong] (64 bits) from the [BitSource] in little endian and LSB order.
 *
 * @return The unsigned long read.
 */
fun BitSource.readULongLeLsb(): ULong = readULongLsb().reverseBytes()