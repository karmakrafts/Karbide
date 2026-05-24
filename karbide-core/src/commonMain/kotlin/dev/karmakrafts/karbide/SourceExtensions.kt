package dev.karmakrafts.karbide

import kotlinx.io.Source
import kotlinx.io.readUInt
import kotlinx.io.readULong
import kotlinx.io.readUShort

/**
 * Reads a [Short] value from this [Source] in little-endian byte order.
 *
 * @return the read value.
 */
fun Source.readShortLeFast(): Short = readShort().reverseBytes()

/**
 * Reads an [Int] value from this [Source] in little-endian byte order.
 *
 * @return the read value.
 */
fun Source.readIntLeFast(): Int = readInt().reverseBytes()

/**
 * Reads a [Long] value from this [Source] in little-endian byte order.
 *
 * @return the read value.
 */
fun Source.readLongLeFast(): Long = readLong().reverseBytes()

/**
 * Reads a [Float] value from this [Source] in little-endian byte order.
 *
 * @return the read value.
 */
fun Source.readFloatLeFast(): Float = Float.fromBits(readIntLeFast())

/**
 * Reads a [Double] value from this [Source] in little-endian byte order.
 *
 * @return the read value.
 */
fun Source.readDoubleLeFast(): Double = Double.fromBits(readLongLeFast())

/**
 * Reads a [UShort] value from this [Source] in little-endian byte order.
 *
 * @return the read value.
 */
fun Source.readUShortLeFast(): UShort = readUShort().reverseBytes()

/**
 * Reads a [UInt] value from this [Source] in little-endian byte order.
 *
 * @return the read value.
 */
fun Source.readUIntLeFast(): UInt = readUInt().reverseBytes()

/**
 * Reads a [ULong] value from this [Source] in little-endian byte order.
 *
 * @return the read value.
 */
fun Source.readULongLeFast(): ULong = readULong().reverseBytes()