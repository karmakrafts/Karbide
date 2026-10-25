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
(x) => {
    x = x | 0;
    return (
        (((x & 0xFF) << 8) |
         ((x >>> 8) & 0xFF)) << 16 >> 16
    ) | 0;
}
"""
)
private external fun reverseBytesImpl(x: Short): Short

public actual fun Short.reverseBytes(): Short = reverseBytesImpl(this)

@JsFun(
    """
(x) => {
    x = x | 0;
    return (
        ((x & 0xFF) << 24) |
        ((x & 0xFF00) << 8) |
        ((x >>> 8) & 0xFF00) |
        ((x >>> 24) & 0xFF)
    ) | 0;
}
"""
)
private external fun reverseBytesImpl(x: Int): Int

public actual fun Int.reverseBytes(): Int = reverseBytesImpl(this)

@JsFun(
    """
(x) => {
    let lo = Number(BigInt.asIntN(32, x)) | 0;
    let hi = Number(x >> BigInt(32)) | 0;
    lo = (
        ((lo & 0xFF) << 24) |
        ((lo & 0xFF00) << 8) |
        ((lo >>> 8) & 0xFF00) |
        ((lo >>> 24) & 0xFF)
    ) | 0;
    hi = (
        ((hi & 0xFF) << 24) |
        ((hi & 0xFF00) << 8) |
        ((hi >>> 8) & 0xFF00) |
        ((hi >>> 24) & 0xFF)
    ) | 0;
    return (BigInt(lo) << BigInt(32)) | BigInt(hi >>> 0);
}
"""
)
private external fun reverseBytesImpl(x: Long): Long

public actual fun Long.reverseBytes(): Long = reverseBytesImpl(this)