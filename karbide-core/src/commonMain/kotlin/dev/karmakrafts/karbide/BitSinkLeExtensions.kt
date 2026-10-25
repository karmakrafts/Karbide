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
 * Write a [Short] (16 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The short value to write.
 */
public fun BitSink.writeShortLe(value: Short): Unit = writeBits(Short.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write an [Int] (32 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The integer value to write.
 */
public fun BitSink.writeIntLe(value: Int): Unit = writeBits(Int.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write a [Long] (64 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The long value to write.
 */
public fun BitSink.writeLongLe(value: Long): Unit = writeBits(Long.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write a [Float] (32 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The float value to write.
 */
public fun BitSink.writeFloatLe(value: Float): Unit =
    writeBits(Float.SIZE_BITS, value.toRawBits().reverseBytes().toULong())

/**
 * Write a [Double] (64 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The double value to write.
 */
public fun BitSink.writeDoubleLe(value: Double): Unit =
    writeBits(Double.SIZE_BITS, value.toRawBits().reverseBytes().toULong())

/**
 * Write a [UShort] (16 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The unsigned short value to write.
 */
public fun BitSink.writeUShortLe(value: UShort): Unit = writeBits(UShort.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write a [UInt] (32 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The unsigned integer value to write.
 */
public fun BitSink.writeUIntLe(value: UInt): Unit = writeBits(UInt.SIZE_BITS, value.reverseBytes().toULong())

/**
 * Write a [ULong] (64 bits) to the [BitSink] in little endian byte order.
 *
 * @param value The unsigned long value to write.
 */
public fun BitSink.writeULongLe(value: ULong): Unit = writeBits(ULong.SIZE_BITS, value.reverseBytes())