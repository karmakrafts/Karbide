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
 * These intrinsics are handwritten inline JS to force a more strict ASM.JS coercion.
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

actual fun Byte.reverseBits(count: Int): Byte = reverseBitsImpl(this, count)

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

actual fun Short.reverseBits(count: Int): Short = reverseBitsImpl(this, count)

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

actual fun Int.reverseBits(count: Int): Int = reverseBitsImpl(this, count)

@JsFun(
    """
(x, count) => {
    const names = Object.keys(x);
    const loName = names[0];
    const hiName = names[1];
    var lo = x[loName] | 0;
    var hi = x[hiName] | 0;
    count = count | 0;
    lo = ((lo >>> 1) & 0x55555555) | ((lo & 0x55555555) << 1);
    lo = ((lo >>> 2) & 0x33333333) | ((lo & 0x33333333) << 2);
    lo = ((lo >>> 4) & 0x0F0F0F0F) | ((lo & 0x0F0F0F0F) << 4);
    lo = ((lo >>> 8) & 0x00FF00FF) | ((lo & 0x00FF00FF) << 8);
    lo = (lo >>> 16) | (lo << 16);
    hi = ((hi >>> 1) & 0x55555555) | ((hi & 0x55555555) << 1);
    hi = ((hi >>> 2) & 0x33333333) | ((hi & 0x33333333) << 2);
    hi = ((hi >>> 4) & 0x0F0F0F0F) | ((hi & 0x0F0F0F0F) << 4);
    hi = ((hi >>> 8) & 0x00FF00FF) | ((hi & 0x00FF00FF) << 8);
    hi = (hi >>> 16) | (hi << 16);
    const result = Object.create(Object.getPrototypeOf(x));
    if (count <= 32) {
        result[loName] = ((lo >>> (32 - count)) & ((count | -count) >> 31)) | 0;
        result[hiName] = 0;
        return result;
    }
    count = (64 - count) | 0;
    result[loName] = ((hi >>> count) | ((lo << (32 - count)) & ((count | -count) >> 31))) | 0;
    result[hiName] = (lo >>> count) | 0;
    return result;
}
"""
)
private external fun reverseBitsImpl(x: Long, count: Int): Long

actual fun Long.reverseBits(count: Int): Long = reverseBitsImpl(this, count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun UByte.reverseBits(count: Int): UByte = toByte().reverseBits(count).toUByte()

@Suppress("NOTHING_TO_INLINE")
actual inline fun UShort.reverseBits(count: Int): UShort = toShort().reverseBits(count).toUShort()

@Suppress("NOTHING_TO_INLINE")
actual inline fun UInt.reverseBits(count: Int): UInt = toInt().reverseBits(count).toUInt()

@Suppress("NOTHING_TO_INLINE")
actual inline fun ULong.reverseBits(count: Int): ULong = toLong().reverseBits(count).toULong()