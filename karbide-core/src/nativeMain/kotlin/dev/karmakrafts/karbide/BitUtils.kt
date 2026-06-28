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

@file:OptIn(ExperimentalForeignApi::class)

package dev.karmakrafts.karbide

import dev.karmakrafts.karbide.builtins.karbide_bitreverse32
import dev.karmakrafts.karbide.builtins.karbide_bitreverse64
import kotlinx.cinterop.ExperimentalForeignApi

/**
 * For 8 and 16-bit values, the common implementation is faster because the
 * interop overhead crushes the throughput gain.
 */

@Suppress("NOTHING_TO_INLINE")
actual inline fun UByte.reverseBits(count: Int): UByte = reverseBitsCommon(count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun UShort.reverseBits(count: Int): UShort = reverseBitsCommon(count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun UInt.reverseBits(count: Int): UInt = karbide_bitreverse32(this) shr (UInt.SIZE_BITS - count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun ULong.reverseBits(count: Int): ULong = karbide_bitreverse64(this) shr (ULong.SIZE_BITS - count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun Byte.reverseBits(count: Int): Byte = toUByte().reverseBits(count).toByte()

@Suppress("NOTHING_TO_INLINE")
actual inline fun Short.reverseBits(count: Int): Short = toUShort().reverseBits(count).toShort()

@Suppress("NOTHING_TO_INLINE")
actual inline fun Int.reverseBits(count: Int): Int = toUInt().reverseBits(count).toInt()

@Suppress("NOTHING_TO_INLINE")
actual inline fun Long.reverseBits(count: Int): Long = toULong().reverseBits(count).toLong()