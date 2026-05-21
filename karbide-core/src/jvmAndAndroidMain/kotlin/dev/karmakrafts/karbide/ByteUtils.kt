@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") @file:JvmName("ByteUtils$")

package dev.karmakrafts.karbide

import java.lang.Integer as JInt
import java.lang.Long as JLong
import java.lang.Short as JShort

actual fun Short.reverseBytes(): Short = JShort.reverseBytes(this)
actual fun Int.reverseBytes(): Int = JInt.reverseBytes(this)
actual fun Long.reverseBytes(): Long = JLong.reverseBytes(this)