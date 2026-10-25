/*
 * Copyright 2026 Karma Krafts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.karmakrafts.karbide

/**
 * Write a [Short] (16 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The short value to write.
 */
public fun BitSink.writeShortLeLsb(value: Short): Unit = writeShortLsb(value.reverseBytes())

/**
 * Write an [Int] (32 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The integer value to write.
 */
public fun BitSink.writeIntLeLsb(value: Int): Unit = writeIntLsb(value.reverseBytes())

/**
 * Write a [Long] (64 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The long value to write.
 */
public fun BitSink.writeLongLeLsb(value: Long): Unit = writeLongLsb(value.reverseBytes())

/**
 * Write a [Float] (32 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The float value to write.
 */
public fun BitSink.writeFloatLeLsb(value: Float): Unit = writeIntLeLsb(value.toRawBits())

/**
 * Write a [Double] (64 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The double value to write.
 */
public fun BitSink.writeDoubleLeLsb(value: Double): Unit = writeLongLeLsb(value.toRawBits())

/**
 * Write a [UShort] (16 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The unsigned short value to write.
 */
public fun BitSink.writeUShortLeLsb(value: UShort): Unit = writeUShortLsb(value.reverseBytes())

/**
 * Write a [UInt] (32 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The unsigned integer value to write.
 */
public fun BitSink.writeUIntLeLsb(value: UInt): Unit = writeUIntLsb(value.reverseBytes())

/**
 * Write a [ULong] (64 bits) to the [BitSink] in little endian and LSB order.
 *
 * @param value The unsigned long value to write.
 */
public fun BitSink.writeULongLeLsb(value: ULong): Unit = writeULongLsb(value.reverseBytes())