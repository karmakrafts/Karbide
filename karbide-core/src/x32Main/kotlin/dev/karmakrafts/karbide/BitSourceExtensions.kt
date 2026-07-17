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
 * Read the specified number of bits from the source.
 *
 * @param count The number of bits to read.
 * @return The bits read as a [UInt].
 */
fun BitSource.readBits32(count: Int): UInt {
    require(this is BitSource32)
    return readBits32(count)
}

/**
 * Read the specified number of bits without consuming them.
 *
 * @param count The number of bits to peek.
 * @return The bits read as a [UInt].
 */
fun BitSource.peekBits32(count: Int): UInt {
    require(this is BitSource32)
    return peekBits32(count)
}