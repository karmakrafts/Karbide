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