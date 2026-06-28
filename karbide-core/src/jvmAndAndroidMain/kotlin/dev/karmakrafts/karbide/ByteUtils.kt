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

@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") @file:JvmName("ByteUtils$")

package dev.karmakrafts.karbide

import java.lang.Integer as JInt
import java.lang.Long as JLong
import java.lang.Short as JShort

actual fun Short.reverseBytes(): Short = JShort.reverseBytes(this)
actual fun Int.reverseBytes(): Int = JInt.reverseBytes(this)
actual fun Long.reverseBytes(): Long = JLong.reverseBytes(this)