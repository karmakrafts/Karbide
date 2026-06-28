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
 * Reverses the byte order of the [Short] value.
 *
 * @return The value with its bytes reversed.
 */
expect fun Short.reverseBytes(): Short

/**
 * Reverses the byte order of the [Int] value.
 *
 * @return The value with its bytes reversed.
 */
expect fun Int.reverseBytes(): Int

/**
 * Reverses the byte order of the [Long] value.
 *
 * @return The value with its bytes reversed.
 */
expect fun Long.reverseBytes(): Long

/**
 * Reverses the byte order of the [UShort] value.
 *
 * @return The value with its bytes reversed.
 */
fun UShort.reverseBytes(): UShort = toShort().reverseBytes().toUShort()

/**
 * Reverses the byte order of the [UInt] value.
 *
 * @return The value with its bytes reversed.
 */
fun UInt.reverseBytes(): UInt = toInt().reverseBytes().toUInt()

/**
 * Reverses the byte order of the [ULong] value.
 *
 * @return The value with its bytes reversed.
 */
fun ULong.reverseBytes(): ULong = toLong().reverseBytes().toULong()