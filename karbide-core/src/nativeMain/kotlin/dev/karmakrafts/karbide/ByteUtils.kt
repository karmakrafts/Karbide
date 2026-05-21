package dev.karmakrafts.karbide

import platform.builtin.builtin_bswap16
import platform.builtin.builtin_bswap32
import platform.builtin.builtin_bswap64

actual fun Short.reverseBytes(): Short = builtin_bswap16(this)
actual fun Int.reverseBytes(): Int = builtin_bswap32(this)
actual fun Long.reverseBytes(): Long = builtin_bswap64(this)