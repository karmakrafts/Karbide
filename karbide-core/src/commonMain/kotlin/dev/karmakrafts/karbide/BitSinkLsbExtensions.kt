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
 * Write the specified number of bits to the [BitSink] in least significant bit first order.
 *
 * @param count The number of bits to write.
 * @param bits The bits to write as a [ULong].
 */
public fun BitSink.writeBitsLsb(count: Int, bits: ULong): Unit = writeBits(count, bits.reverseBits(count))

/**
 * Write a [Byte] (8 bits) to the [BitSink] in LSB order.
 *
 * @param value The byte value to write.
 */
public fun BitSink.writeByteLsb(value: Byte): Unit = writeBitsLsb(Byte.SIZE_BITS, value.toULong())

/**
 * Write a [Short] (16 bits) to the [BitSink] in LSB order.
 *
 * @param value The short value to write.
 */
public fun BitSink.writeShortLsb(value: Short): Unit = writeBitsLsb(Short.SIZE_BITS, value.toULong())

/**
 * Write an [Int] (32 bits) to the [BitSink] in LSB order.
 *
 * @param value The integer value to write.
 */
public fun BitSink.writeIntLsb(value: Int): Unit = writeBitsLsb(Int.SIZE_BITS, value.toULong())

/**
 * Write a [Long] (64 bits) to the [BitSink] in LSB order.
 *
 * @param value The long value to write.
 */
public fun BitSink.writeLongLsb(value: Long): Unit = writeBitsLsb(Long.SIZE_BITS, value.toULong())

/**
 * Write a [Float] (32 bits) to the [BitSink] in LSB order.
 *
 * @param value The float value to write.
 */
public fun BitSink.writeFloatLsb(value: Float): Unit = writeIntLsb(value.toRawBits())

/**
 * Write a [Double] (64 bits) to the [BitSink] in LSB order.
 *
 * @param value The double value to write.
 */
public fun BitSink.writeDoubleLsb(value: Double): Unit = writeLongLsb(value.toRawBits())

/**
 * Write a [UByte] (8 bits) to the [BitSink] in LSB order.
 *
 * @param value The unsigned byte value to write.
 */
public fun BitSink.writeUByteLsb(value: UByte): Unit = writeBitsLsb(Byte.SIZE_BITS, value.toULong())

/**
 * Write a [UShort] (16 bits) to the [BitSink] in LSB order.
 *
 * @param value The unsigned short value to write.
 */
public fun BitSink.writeUShortLsb(value: UShort): Unit = writeBitsLsb(Short.SIZE_BITS, value.toULong())

/**
 * Write a [UInt] (32 bits) to the [BitSink] in LSB order.
 *
 * @param value The unsigned integer value to write.
 */
public fun BitSink.writeUIntLsb(value: UInt): Unit = writeBitsLsb(Int.SIZE_BITS, value.toULong())

/**
 * Write a [ULong] (64 bits) to the [BitSink] in LSB order.
 *
 * @param value The unsigned long value to write.
 */
public fun BitSink.writeULongLsb(value: ULong): Unit = writeBitsLsb(Long.SIZE_BITS, value)

/**
 * Write a [ByteArray] to the [BitSink] with each byte in LSB order.
 *
 * @param value The byte array to write.
 */
public fun BitSink.writeByteArrayLsb(value: ByteArray) {
    for (byte in value) {
        writeByteLsb(byte)
    }
}

/**
 * Write a [String] to the [BitSink] in LSB order.
 *
 * @param value The string value to write.
 */
public fun BitSink.writeStringLsb(value: String): Unit = writeByteArrayLsb(value.encodeToByteArray())