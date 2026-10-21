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

@file:OptIn(ExperimentalWasmJsInterop::class)

package dev.karmakrafts.karbide

/**
 * These intrinsics use handwritten inline JS for fixed-width integer operations.
 */

@JsFun(
    """
(x, count) => {
    x = x | 0;
    count = count | 0;
    x = ((x >>> 1) & 0x55) | ((x & 0x55) << 1);
    x = ((x >>> 2) & 0x33) | ((x & 0x33) << 2);
    x = ((x >>> 4) & 0x0F) | ((x & 0x0F) << 4);
    x = x >>> (8 - count);
    return (x << 24 >> 24) | 0;
}
"""
)
private external fun reverseBitsImpl(x: Byte, count: Int): Byte

actual fun Byte.reverseBits(count: Int): Byte =
    if (count == 0) reverseBitsCommon(count) else reverseBitsImpl(this, count)

@JsFun(
    """
(x, count) => {
    x = x | 0;
    count = count | 0;
    x = ((x >>> 1) & 0x5555) | ((x & 0x5555) << 1);
    x = ((x >>> 2) & 0x3333) | ((x & 0x3333) << 2);
    x = ((x >>> 4) & 0x0F0F) | ((x & 0x0F0F) << 4);
    x = ((x >>> 8) & 0x00FF) | ((x & 0x00FF) << 8);
    x = x >>> (16 - count);
    return (x << 16 >> 16) | 0;
}
"""
)
private external fun reverseBitsImpl(x: Short, count: Int): Short

actual fun Short.reverseBits(count: Int): Short =
    if (count == 0) reverseBitsCommon(count) else reverseBitsImpl(this, count)

@JsFun(
    """
(x, count) => {
    x = x | 0;
    count = count | 0;
    x = ((x >>> 1) & 0x55555555) | ((x & 0x55555555) << 1);
    x = ((x >>> 2) & 0x33333333) | ((x & 0x33333333) << 2);
    x = ((x >>> 4) & 0x0F0F0F0F) | ((x & 0x0F0F0F0F) << 4);
    x = ((x >>> 8) & 0x00FF00FF) | ((x & 0x00FF00FF) << 8);
    x = (x >>> 16) | (x << 16);
    return (x >>> (32 - count)) | 0;
}
"""
)
private external fun reverseBitsImpl(x: Int, count: Int): Int

actual fun Int.reverseBits(count: Int): Int = if (count == 0) reverseBitsCommon(count) else reverseBitsImpl(this, count)

@JsFun(
    """
(x, count) => {
    x = BigInt.asUintN(64, x);
    x = ((x >> BigInt(1)) & BigInt("0x5555555555555555")) | ((x & BigInt("0x5555555555555555")) << BigInt(1));
    x = ((x >> BigInt(2)) & BigInt("0x3333333333333333")) | ((x & BigInt("0x3333333333333333")) << BigInt(2));
    x = ((x >> BigInt(4)) & BigInt("0x0F0F0F0F0F0F0F0F")) | ((x & BigInt("0x0F0F0F0F0F0F0F0F")) << BigInt(4));
    x = ((x >> BigInt(8)) & BigInt("0x00FF00FF00FF00FF")) | ((x & BigInt("0x00FF00FF00FF00FF")) << BigInt(8));
    x = ((x >> BigInt(16)) & BigInt("0x0000FFFF0000FFFF")) | ((x & BigInt("0x0000FFFF0000FFFF")) << BigInt(16));
    x = (x >> BigInt(32)) | ((x & BigInt("0xFFFFFFFF")) << BigInt(32));
    return BigInt.asIntN(64, x >> BigInt(64 - count));
}
"""
)
private external fun reverseBitsImpl(x: Long, count: Int): Long

actual fun Long.reverseBits(count: Int): Long =
    if (count == 0) reverseBitsCommon(count) else reverseBitsImpl(this, count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun UByte.reverseBits(count: Int): UByte = toByte().reverseBits(count).toUByte()

@Suppress("NOTHING_TO_INLINE")
actual inline fun UShort.reverseBits(count: Int): UShort = toShort().reverseBits(count).toUShort()

@Suppress("NOTHING_TO_INLINE")
actual inline fun UInt.reverseBits(count: Int): UInt = toInt().reverseBits(count).toUInt()

@Suppress("NOTHING_TO_INLINE")
actual inline fun ULong.reverseBits(count: Int): ULong = toLong().reverseBits(count).toULong()