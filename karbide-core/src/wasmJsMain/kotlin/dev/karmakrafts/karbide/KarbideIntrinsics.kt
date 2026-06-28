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

@file:OptIn(ExperimentalWasmInterop::class, ExperimentalWasmJsInterop::class)

package dev.karmakrafts.karbide

import js.typedarrays.toInt8Array
import web.assembly.instantiate
import kotlin.wasm.ExperimentalWasmInterop

internal external interface KarbideIntrinsicsExports : JsAny {
    fun reverseBitsByte(value: Byte, count: Int): Byte
    fun reverseBitsShort(value: Short, count: Int): Short
    fun reverseBitsInt(value: Int, count: Int): Int
    fun reverseBitsLong(value: Long, count: Int): Long
}

object KarbideIntrinsics {
    var areAvailable: Boolean = false
        private set

    @PublishedApi
    internal lateinit var exports: KarbideIntrinsicsExports

    /**
     * This has be invoked manually before using any Karbide functions
     * if WASM intrinsics should be used. Otherwise, a common Kotlin fallback will be used.
     */
    suspend fun init() {
        val wasmSource = instantiate(karbideIntrinsicsBlob.toInt8Array())
        exports = wasmSource.instance.exports.unsafeCast()
        areAvailable = true
    }

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun reverseBits(value: Byte, count: Int): Byte = exports.reverseBitsByte(value, count)

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun reverseBits(value: Short, count: Int): Short = exports.reverseBitsShort(value, count)

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun reverseBits(value: Int, count: Int): Int = exports.reverseBitsInt(value, count)

    @Suppress("NOTHING_TO_INLINE")
    internal inline fun reverseBits(value: Long, count: Int): Long = exports.reverseBitsLong(value, count)
}