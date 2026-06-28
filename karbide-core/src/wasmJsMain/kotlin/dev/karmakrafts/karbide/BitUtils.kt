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

@Suppress("NOTHING_TO_INLINE")
actual fun Byte.reverseBits(count: Int): Byte {
    return if (KarbideIntrinsics.areAvailable) KarbideIntrinsics.reverseBits(this, count)
    else reverseBitsCommon(count)
}

@Suppress("NOTHING_TO_INLINE")
actual fun Short.reverseBits(count: Int): Short {
    return if (KarbideIntrinsics.areAvailable) KarbideIntrinsics.reverseBits(this, count)
    else reverseBitsCommon(count)
}

@Suppress("NOTHING_TO_INLINE")
actual fun Int.reverseBits(count: Int): Int {
    return if (KarbideIntrinsics.areAvailable) KarbideIntrinsics.reverseBits(this, count)
    else reverseBitsCommon(count)
}

@Suppress("NOTHING_TO_INLINE")
actual fun Long.reverseBits(count: Int): Long {
    return if (KarbideIntrinsics.areAvailable) KarbideIntrinsics.reverseBits(this, count)
    else reverseBitsCommon(count)
}

@Suppress("NOTHING_TO_INLINE")
actual inline fun UByte.reverseBits(count: Int): UByte = reverseBitsCommon(count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun UShort.reverseBits(count: Int): UShort = reverseBitsCommon(count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun UInt.reverseBits(count: Int): UInt = reverseBitsCommon(count)

@Suppress("NOTHING_TO_INLINE")
actual inline fun ULong.reverseBits(count: Int): ULong = reverseBitsCommon(count)