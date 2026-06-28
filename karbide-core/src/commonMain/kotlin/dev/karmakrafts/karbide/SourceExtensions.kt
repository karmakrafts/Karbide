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