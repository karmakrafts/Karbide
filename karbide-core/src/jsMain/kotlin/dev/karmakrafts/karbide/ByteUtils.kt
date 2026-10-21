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

actual fun Short.reverseBytes(): Short = reverseBytesImpl(this)

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

actual fun Int.reverseBytes(): Int = reverseBytesImpl(this)

@JsFun(
    """
(x) => {
    x = BigInt.asUintN(64, x);
    x = ((x >> BigInt(8)) & BigInt("0x00FF00FF00FF00FF")) | ((x & BigInt("0x00FF00FF00FF00FF")) << BigInt(8));
    x = ((x >> BigInt(16)) & BigInt("0x0000FFFF0000FFFF")) | ((x & BigInt("0x0000FFFF0000FFFF")) << BigInt(16));
    x = (x >> BigInt(32)) | ((x & BigInt("0xFFFFFFFF")) << BigInt(32));
    return BigInt.asIntN(64, x);
}
"""
)
private external fun reverseBytesImpl(x: Long): Long

actual fun Long.reverseBytes(): Long = reverseBytesImpl(this)