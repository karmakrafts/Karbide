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

@file:JvmName("BitUtils$") @file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package dev.karmakrafts.karbide

import java.lang.Integer as JInt
import java.lang.Long as JLong

actual fun Byte.reverseBits(count: Int): Byte =
    if (count == 0) 0.toByte() else (JInt.reverse(toInt()) ushr (Int.SIZE_BITS - count)).toByte()

actual fun Short.reverseBits(count: Int): Short =
    if (count == 0) 0.toShort() else (JInt.reverse(toInt()) ushr (Int.SIZE_BITS - count)).toShort()

actual fun Int.reverseBits(count: Int): Int = if (count == 0) 0 else JInt.reverse(this) ushr (Int.SIZE_BITS - count)

actual fun Long.reverseBits(count: Int): Long =
    if (count == 0) 0L else JLong.reverse(this) ushr (Long.SIZE_BITS - count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun UByte.reverseBits(count: Int): UByte = toByte().reverseBits(count).toUByte()

@Suppress("NOTHING_TO_INLINE")
actual inline fun UShort.reverseBits(count: Int): UShort = toShort().reverseBits(count).toUShort()

@Suppress("NOTHING_TO_INLINE")
actual inline fun UInt.reverseBits(count: Int): UInt = toInt().reverseBits(count).toUInt()

@Suppress("NOTHING_TO_INLINE")
actual inline fun ULong.reverseBits(count: Int): ULong = toLong().reverseBits(count).toULong()