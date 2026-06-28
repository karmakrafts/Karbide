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
 * Read a [Short] (16 bits) from the [BitSource] in little endian and LSB order
 * without consuming the bits.
 *
 * @return The short read.
 */
fun BitSource.peekShortLeLsb(): Short = peekShortLsb().reverseBytes()

/**
 * Read an [Int] (32 bits) from the [BitSource] in little endian and LSB order
 * without consuming the bits.
 *
 * @return The integer read.
 */
fun BitSource.peekIntLeLsb(): Int = peekIntLsb().reverseBytes()

/**
 * Read a [Long] (64 bits) from the [BitSource] in little endian and LSB order
 * without consuming the bits.
 *
 * @return The long read.
 */
fun BitSource.peekLongLeLsb(): Long = peekLongLsb().reverseBytes()

/**
 * Read a [Float] (32 bits) from the [BitSource] in little endian and LSB order
 * without consuming the bits.
 *
 * @return The float read.
 */
fun BitSource.peekFloatLeLsb(): Float = Float.fromBits(peekIntLeLsb())

/**
 * Read a [Double] (64 bits) from the [BitSource] in little endian and LSB order
 * without consuming the bits.
 *
 * @return The double read.
 */
fun BitSource.peekDoubleLeLsb(): Double = Double.fromBits(peekLongLeLsb())

/**
 * Read a [UShort] (16 bits) from the [BitSource] in little endian and LSB order
 * without consuming the bits.
 *
 * @return The unsigned short read.
 */
fun BitSource.peekUShortLeLsb(): UShort = peekUShortLsb().reverseBytes()

/**
 * Read a [UInt] (32 bits) from the [BitSource] in little endian and LSB order
 * without consuming the bits.
 *
 * @return The unsigned integer read.
 */
fun BitSource.peekUIntLeLsb(): UInt = peekUIntLsb().reverseBytes()

/**
 * Read a [ULong] (64 bits) from the [BitSource] in little endian and LSB order
 * without consuming the bits.
 *
 * @return The unsigned long read.
 */
fun BitSource.peekULongLeLsb(): ULong = peekULongLsb().reverseBytes()

// ------------------------------ Reading ------------------------------

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