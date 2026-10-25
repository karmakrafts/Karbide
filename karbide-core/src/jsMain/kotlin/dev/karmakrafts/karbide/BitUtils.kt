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

public actual fun Byte.reverseBits(count: Int): Byte =
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

public actual fun Short.reverseBits(count: Int): Short =
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

public actual fun Int.reverseBits(count: Int): Int =
    if (count == 0) reverseBitsCommon(count) else reverseBitsImpl(this, count)

@JsFun(
    """
(x, count) => {
    count = count | 0;
    let lo = Number(BigInt.asIntN(32, x)) | 0;
    let hi = Number(x >> BigInt(32)) | 0;
    lo = (((lo >>> 1) & 0x55555555) | ((lo & 0x55555555) << 1)) | 0;
    lo = (((lo >>> 2) & 0x33333333) | ((lo & 0x33333333) << 2)) | 0;
    lo = (((lo >>> 4) & 0x0F0F0F0F) | ((lo & 0x0F0F0F0F) << 4)) | 0;
    lo = (((lo >>> 8) & 0x00FF00FF) | ((lo & 0x00FF00FF) << 8)) | 0;
    lo = ((lo >>> 16) | (lo << 16)) | 0;
    hi = (((hi >>> 1) & 0x55555555) | ((hi & 0x55555555) << 1)) | 0;
    hi = (((hi >>> 2) & 0x33333333) | ((hi & 0x33333333) << 2)) | 0;
    hi = (((hi >>> 4) & 0x0F0F0F0F) | ((hi & 0x0F0F0F0F) << 4)) | 0;
    hi = (((hi >>> 8) & 0x00FF00FF) | ((hi & 0x00FF00FF) << 8)) | 0;
    hi = ((hi >>> 16) | (hi << 16)) | 0;
    let shift = (64 - count) | 0;
    if (shift >= 32) {
        hi = (lo >>> (shift - 32)) | 0;
        lo = 0;
    }
    else if (shift > 0) {
        hi = ((hi >>> shift) | (lo << (32 - shift))) | 0;
        lo = (lo >>> shift) | 0;
    }
    return (BigInt(lo) << BigInt(32)) | BigInt(hi >>> 0);
}
"""
)
private external fun reverseBitsImpl(x: Long, count: Int): Long

public actual fun Long.reverseBits(count: Int): Long =
    if (count == 0) reverseBitsCommon(count) else reverseBitsImpl(this, count)

@Suppress("NOTHING_TO_INLINE")
public actual inline fun UByte.reverseBits(count: Int): UByte = toByte().reverseBits(count).toUByte()

@Suppress("NOTHING_TO_INLINE")
public actual inline fun UShort.reverseBits(count: Int): UShort = toShort().reverseBits(count).toUShort()

@Suppress("NOTHING_TO_INLINE")
public actual inline fun UInt.reverseBits(count: Int): UInt = toInt().reverseBits(count).toUInt()

@Suppress("NOTHING_TO_INLINE")
public actual inline fun ULong.reverseBits(count: Int): ULong = toLong().reverseBits(count).toULong()