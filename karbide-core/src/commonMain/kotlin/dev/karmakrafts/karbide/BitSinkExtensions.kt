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
 * Write a single bit to the [BitSink].
 *
 * @param value The bit value to write (0 or 1).
 */
fun BitSink.writeBit(value: UByte) = writeBits(1, value.toULong())

/**
 * Write a nibble (4 bits) to the [BitSink].
 *
 * @param value The nibble value to write.
 */
fun BitSink.writeNibble(value: UByte) = writeBits(4, value.toULong())

/**
 * Write a [Byte] (8 bits) to the [BitSink].
 *
 * @param value The byte value to write.
 */
fun BitSink.writeByte(value: Byte) = writeBits(Byte.SIZE_BITS, value.toULong())

/**
 * Write a [Short] (16 bits) to the [BitSink].
 *
 * @param value The short value to write.
 */
fun BitSink.writeShort(value: Short) = writeBits(Short.SIZE_BITS, value.toULong())

/**
 * Write an [Int] (32 bits) to the [BitSink].
 *
 * @param value The integer value to write.
 */
fun BitSink.writeInt(value: Int) = writeBits(Int.SIZE_BITS, value.toULong())

/**
 * Write a [Long] (64 bits) to the [BitSink].
 *
 * @param value The long value to write.
 */
fun BitSink.writeLong(value: Long) = writeBits(Long.SIZE_BITS, value.toULong())

/**
 * Write a [Float] (32 bits) to the [BitSink].
 *
 * @param value The float value to write.
 */
fun BitSink.writeFloat(value: Float) = writeBits(Float.SIZE_BITS, value.toRawBits().toULong())

/**
 * Write a [Double] (64 bits) to the [BitSink].
 *
 * @param value The double value to write.
 */
fun BitSink.writeDouble(value: Double) = writeBits(Double.SIZE_BITS, value.toRawBits().toULong())

/**
 * Write a [UByte] (8 bits) to the [BitSink].
 *
 * @param value The unsigned byte value to write.
 */
fun BitSink.writeUByte(value: UByte) = writeBits(UByte.SIZE_BITS, value.toULong())

/**
 * Write a [UShort] (16 bits) to the [BitSink].
 *
 * @param value The unsigned short value to write.
 */
fun BitSink.writeUShort(value: UShort) = writeBits(UShort.SIZE_BITS, value.toULong())

/**
 * Write a [UInt] (32 bits) to the [BitSink].
 *
 * @param value The unsigned integer value to write.
 */
fun BitSink.writeUInt(value: UInt) = writeBits(UInt.SIZE_BITS, value.toULong())

/**
 * Write a [ULong] (64 bits) to the [BitSink].
 *
 * @param value The unsigned long value to write.
 */
fun BitSink.writeULong(value: ULong) = writeBits(ULong.SIZE_BITS, value)

/**
 * Write a [ByteArray] to the [BitSink].
 *
 * @param value The byte array to write.
 */
fun BitSink.writeByteArray(value: ByteArray) {
    for (byte in value) {
        writeByte(byte)
    }
}

/**
 * Write a [String] to the [BitSink].
 *
 * @param value The string value to write.
 */
fun BitSink.writeString(value: String) = writeByteArray(value.encodeToByteArray())

/**
 * Write a single padding bit to the [BitSink].
 *
 * @param value The bit value to write (0 or 1).
 */
fun BitSink.padBit(value: UByte) = padBits(1, value)

/**
 * Write the specified number of padding nibbles (4 bits each) to the [BitSink].
 *
 * @param count The number of nibbles to write.
 * @param value The bit value to write (0 or 1).
 */
fun BitSink.padNibbles(count: Int, value: UByte) = padBits(count shl 2, value)

/**
 * Write a single padding nibble (4 bits) to the [BitSink].
 *
 * @param value The bit value to write (0 or 1).
 */
fun BitSink.padNibble(value: UByte) = padNibbles(1, value)

/**
 * Write the specified number of padding bytes (8 bits each) to the [BitSink].
 *
 * @param count The number of bytes to write.
 * @param value The bit value to write (0 or 1).
 */
fun BitSink.padBytes(count: Int, value: UByte) = padBits(count shl 3, value)

/**
 * Write a single padding byte to the [BitSink].
 *
 * @param value The bit value to write (0 or 1).
 */
fun BitSink.padByte(value: UByte) = padBytes(1, value)