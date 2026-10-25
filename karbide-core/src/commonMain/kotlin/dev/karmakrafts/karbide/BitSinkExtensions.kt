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
public fun BitSink.writeBit(value: UByte): Unit = writeBits(1, value.toULong())

/**
 * Write a nibble (4 bits) to the [BitSink].
 *
 * @param value The nibble value to write.
 */
public fun BitSink.writeNibble(value: UByte): Unit = writeBits(4, value.toULong())

/**
 * Write a [Byte] (8 bits) to the [BitSink].
 *
 * @param value The byte value to write.
 */
public fun BitSink.writeByte(value: Byte): Unit = writeBits(Byte.SIZE_BITS, value.toULong())

/**
 * Write a [Short] (16 bits) to the [BitSink].
 *
 * @param value The short value to write.
 */
public fun BitSink.writeShort(value: Short): Unit = writeBits(Short.SIZE_BITS, value.toULong())

/**
 * Write an [Int] (32 bits) to the [BitSink].
 *
 * @param value The integer value to write.
 */
public fun BitSink.writeInt(value: Int): Unit = writeBits(Int.SIZE_BITS, value.toULong())

/**
 * Write a [Long] (64 bits) to the [BitSink].
 *
 * @param value The long value to write.
 */
public fun BitSink.writeLong(value: Long): Unit = writeBits(Long.SIZE_BITS, value.toULong())

/**
 * Write a [Float] (32 bits) to the [BitSink].
 *
 * @param value The float value to write.
 */
public fun BitSink.writeFloat(value: Float): Unit = writeBits(Float.SIZE_BITS, value.toRawBits().toULong())

/**
 * Write a [Double] (64 bits) to the [BitSink].
 *
 * @param value The double value to write.
 */
public fun BitSink.writeDouble(value: Double): Unit = writeBits(Double.SIZE_BITS, value.toRawBits().toULong())

/**
 * Write a [UByte] (8 bits) to the [BitSink].
 *
 * @param value The unsigned byte value to write.
 */
public fun BitSink.writeUByte(value: UByte): Unit = writeBits(UByte.SIZE_BITS, value.toULong())

/**
 * Write a [UShort] (16 bits) to the [BitSink].
 *
 * @param value The unsigned short value to write.
 */
public fun BitSink.writeUShort(value: UShort): Unit = writeBits(UShort.SIZE_BITS, value.toULong())

/**
 * Write a [UInt] (32 bits) to the [BitSink].
 *
 * @param value The unsigned integer value to write.
 */
public fun BitSink.writeUInt(value: UInt): Unit = writeBits(UInt.SIZE_BITS, value.toULong())

/**
 * Write a [ULong] (64 bits) to the [BitSink].
 *
 * @param value The unsigned long value to write.
 */
public fun BitSink.writeULong(value: ULong): Unit = writeBits(ULong.SIZE_BITS, value)

/**
 * Write a [ByteArray] to the [BitSink].
 *
 * @param value The byte array to write.
 */
public fun BitSink.writeByteArray(value: ByteArray) {
    for (byte in value) {
        writeByte(byte)
    }
}

/**
 * Write a [String] to the [BitSink].
 *
 * @param value The string value to write.
 */
public fun BitSink.writeString(value: String): Unit = writeByteArray(value.encodeToByteArray())

/**
 * Write a single padding bit to the [BitSink].
 *
 * @param value The bit value to write (0 or 1).
 */
public fun BitSink.padBit(value: UByte): Unit = padBits(1, value)

/**
 * Write the specified number of padding nibbles (4 bits each) to the [BitSink].
 *
 * @param count The number of nibbles to write.
 * @param value The bit value to write (0 or 1).
 */
public fun BitSink.padNibbles(count: Int, value: UByte): Unit = padBits(count shl 2, value)

/**
 * Write a single padding nibble (4 bits) to the [BitSink].
 *
 * @param value The bit value to write (0 or 1).
 */
public fun BitSink.padNibble(value: UByte): Unit = padNibbles(1, value)

/**
 * Write the specified number of padding bytes (8 bits each) to the [BitSink].
 *
 * @param count The number of bytes to write.
 * @param value The bit value to write (0 or 1).
 */
public fun BitSink.padBytes(count: Int, value: UByte): Unit = padBits(count shl 3, value)

/**
 * Write a single padding byte to the [BitSink].
 *
 * @param value The bit value to write (0 or 1).
 */
public fun BitSink.padByte(value: UByte): Unit = padBytes(1, value)