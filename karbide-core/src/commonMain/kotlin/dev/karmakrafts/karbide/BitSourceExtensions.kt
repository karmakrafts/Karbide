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

// ------------------------------ Peeking ------------------------------

/**
 * Read a single bit from the [BitSource]
 * without consuming it.
 *
 * @return The bit read as a [UByte].
 */
public fun BitSource.peekBit(): UByte = peekBits(1).toUByte()

/**
 * Read a nibble (4 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The nibble read as a [UByte].
 */
public fun BitSource.peekNibble(): UByte = peekBits(4).toUByte()

/**
 * Read a [Byte] (8 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The byte read.
 */
public fun BitSource.peekByte(): Byte = peekBits(Byte.SIZE_BITS).toByte()

/**
 * Read a [Short] (16 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The short read.
 */
public fun BitSource.peekShort(): Short = peekBits(Short.SIZE_BITS).toShort()

/**
 * Read an [Int] (32 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The integer read.
 */
public fun BitSource.peekInt(): Int = peekBits(Int.SIZE_BITS).toInt()

/**
 * Read a [Long] (64 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The long read.
 */
public fun BitSource.peekLong(): Long = peekBits(Long.SIZE_BITS).toLong()

/**
 * Read a [Float] (32 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The float read.
 */
public fun BitSource.peekFloat(): Float = Float.fromBits(peekBits(Float.SIZE_BITS).toInt())

/**
 * Read a [Double] (64 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The double read.
 */
public fun BitSource.peekDouble(): Double = Double.fromBits(peekBits(Double.SIZE_BITS).toLong())

/**
 * Read a [UByte] (8 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The unsigned byte read.
 */
public fun BitSource.peekUByte(): UByte = peekBits(UByte.SIZE_BITS).toUByte()

/**
 * Read a [UShort] (16 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The unsigned short read.
 */
public fun BitSource.peekUShort(): UShort = peekBits(UShort.SIZE_BITS).toUShort()

/**
 * Read a [UInt] (32 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The unsigned integer read.
 */
public fun BitSource.peekUInt(): UInt = peekBits(UInt.SIZE_BITS).toUInt()

/**
 * Read a [ULong] (64 bits) from the [BitSource]
 * without consuming the bits.
 *
 * @return The unsigned long read.
 */
public fun BitSource.peekULong(): ULong = peekBits(ULong.SIZE_BITS)

// ------------------------------ Reading ------------------------------

/**
 * Read a single bit from the [BitSource].
 *
 * @return The bit read as a [UByte].
 */
public fun BitSource.readBit(): UByte = readBits(1).toUByte()

/**
 * Read a nibble (4 bits) from the [BitSource].
 *
 * @return The nibble read as a [UByte].
 */
public fun BitSource.readNibble(): UByte = readBits(4).toUByte()

/**
 * Read a [Byte] (8 bits) from the [BitSource].
 *
 * @return The byte read.
 */
public fun BitSource.readByte(): Byte = readBits(Byte.SIZE_BITS).toByte()

/**
 * Read a [Short] (16 bits) from the [BitSource].
 *
 * @return The short read.
 */
public fun BitSource.readShort(): Short = readBits(Short.SIZE_BITS).toShort()

/**
 * Read an [Int] (32 bits) from the [BitSource].
 *
 * @return The integer read.
 */
public fun BitSource.readInt(): Int = readBits(Int.SIZE_BITS).toInt()

/**
 * Read a [Long] (64 bits) from the [BitSource].
 *
 * @return The long read.
 */
public fun BitSource.readLong(): Long = readBits(Long.SIZE_BITS).toLong()

/**
 * Read a [Float] (32 bits) from the [BitSource].
 *
 * @return The float read.
 */
public fun BitSource.readFloat(): Float = Float.fromBits(readBits(Float.SIZE_BITS).toInt())

/**
 * Read a [Double] (64 bits) from the [BitSource].
 *
 * @return The double read.
 */
public fun BitSource.readDouble(): Double = Double.fromBits(readBits(Double.SIZE_BITS).toLong())

/**
 * Read a [UByte] (8 bits) from the [BitSource].
 *
 * @return The unsigned byte read.
 */
public fun BitSource.readUByte(): UByte = readBits(UByte.SIZE_BITS).toUByte()

/**
 * Read a [UShort] (16 bits) from the [BitSource].
 *
 * @return The unsigned short read.
 */
public fun BitSource.readUShort(): UShort = readBits(UShort.SIZE_BITS).toUShort()

/**
 * Read a [UInt] (32 bits) from the [BitSource].
 *
 * @return The unsigned integer read.
 */
public fun BitSource.readUInt(): UInt = readBits(UInt.SIZE_BITS).toUInt()

/**
 * Read a [ULong] (64 bits) from the [BitSource].
 *
 * @return The unsigned long read.
 */
public fun BitSource.readULong(): ULong = readBits(ULong.SIZE_BITS)

/**
 * Read a [ByteArray] of the specified size from the [BitSource].
 *
 * @param count The number of bytes to read.
 * @return The byte array read.
 */
public fun BitSource.readByteArray(count: Int): ByteArray = ByteArray(count) {
    readByte()
}

/**
 * Read a [String] of the specified length from the [BitSource].
 *
 * @param length The number of characters to read.
 * @return The string read.
 */
public fun BitSource.readString(length: Int): String = readByteArray(length).decodeToString()

/**
 * Skip a single bit in the [BitSource].
 */
public fun BitSource.skipBit(): Unit = skipBits(1)

/**
 * Skip the specified number of nibbles (4 bits each) in the [BitSource].
 *
 * @param count The number of nibbles to skip.
 */
public fun BitSource.skipNibbles(count: Int): Unit = skipBits(count shl 2)

/**
 * Skip a nibble (4 bits) in the [BitSource].
 */
public fun BitSource.skipNibble(): Unit = skipNibbles(1)

/**
 * Skip the specified number of bytes (8 bits each) in the [BitSource].
 *
 * @param count The number of bytes to skip.
 */
public fun BitSource.skipBytes(count: Int): Unit = skipBits(count shl 3)

/**
 * Skip a single byte in the [BitSource].
 */
public fun BitSource.skipByte(): Unit = skipBytes(1)