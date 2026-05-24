package dev.karmakrafts.karbide

import kotlinx.io.Sink
import kotlinx.io.writeUInt
import kotlinx.io.writeULong
import kotlinx.io.writeUShort

/**
 * Writes a [Short] value to this [Sink] in little-endian byte order.
 *
 * @param value the value to write.
 */
fun Sink.writeShortLeFast(value: Short) = writeShort(value.reverseBytes())

/**
 * Writes an [Int] value to this [Sink] in little-endian byte order.
 *
 * @param value the value to write.
 */
fun Sink.writeIntLeFast(value: Int) = writeInt(value.reverseBytes())

/**
 * Writes a [Long] value to this [Sink] in little-endian byte order.
 *
 * @param value the value to write.
 */
fun Sink.writeLongLeFast(value: Long) = writeLong(value.reverseBytes())

/**
 * Writes a [Float] value to this [Sink] in little-endian byte order.
 *
 * @param value the value to write.
 */
fun Sink.writeFloatLeFast(value: Float) = writeIntLeFast(value.toBits())

/**
 * Writes a [Double] value to this [Sink] in little-endian byte order.
 *
 * @param value the value to write.
 */
fun Sink.writeDoubleLeFast(value: Double) = writeLongLeFast(value.toBits())

/**
 * Writes a [UShort] value to this [Sink] in little-endian byte order.
 *
 * @param value the value to write.
 */
fun Sink.writeUShortLeFast(value: UShort) = writeUShort(value.reverseBytes())

/**
 * Writes a [UInt] value to this [Sink] in little-endian byte order.
 *
 * @param value the value to write.
 */
fun Sink.writeUIntLeFast(value: UInt) = writeUInt(value.reverseBytes())

/**
 * Writes a [ULong] value to this [Sink] in little-endian byte order.
 *
 * @param value the value to write.
 */
fun Sink.writeULongLeFast(value: ULong) = writeULong(value.reverseBytes())