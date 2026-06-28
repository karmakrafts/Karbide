;;
;; Copyright 2026 Karma Krafts
;;
;; Licensed under the Apache License, Version 2.0 (the "License");
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;;
;; http://www.apache.org/licenses/LICENSE-2.0
;;
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.
;;

(module
  (func $bitreverse32 (param $x i32) (result i32)
    local.get $x
    i32.const 1
    i32.shr_u
    i32.const 0x55555555
    i32.and
    local.get $x
    i32.const 0x55555555
    i32.and
    i32.const 1
    i32.shl
    i32.or
    local.set $x

    local.get $x
    i32.const 2
    i32.shr_u
    i32.const 0x33333333
    i32.and
    local.get $x
    i32.const 0x33333333
    i32.and
    i32.const 2
    i32.shl
    i32.or
    local.set $x

    local.get $x
    i32.const 4
    i32.shr_u
    i32.const 0x0F0F0F0F
    i32.and
    local.get $x
    i32.const 0x0F0F0F0F
    i32.and
    i32.const 4
    i32.shl
    i32.or
    local.set $x

    local.get $x
    i32.const 8
    i32.shr_u
    i32.const 0x00FF00FF
    i32.and
    local.get $x
    i32.const 0x00FF00FF
    i32.and
    i32.const 8
    i32.shl
    i32.or
    local.set $x

    local.get $x
    i32.const 16
    i32.shr_u
    local.get $x
    i32.const 16
    i32.shl
    i32.or
  )

  (func $bitreverse64 (param $x i64) (result i64)
    local.get $x
    i64.const 1
    i64.shr_u
    i64.const 0x5555555555555555
    i64.and
    local.get $x
    i64.const 0x5555555555555555
    i64.and
    i64.const 1
    i64.shl
    i64.or
    local.set $x

    local.get $x
    i64.const 2
    i64.shr_u
    i64.const 0x3333333333333333
    i64.and
    local.get $x
    i64.const 0x3333333333333333
    i64.and
    i64.const 2
    i64.shl
    i64.or
    local.set $x

    local.get $x
    i64.const 4
    i64.shr_u
    i64.const 0x0F0F0F0F0F0F0F0F
    i64.and
    local.get $x
    i64.const 0x0F0F0F0F0F0F0F0F
    i64.and
    i64.const 4
    i64.shl
    i64.or
    local.set $x

    local.get $x
    i64.const 8
    i64.shr_u
    i64.const 0x00FF00FF00FF00FF
    i64.and
    local.get $x
    i64.const 0x00FF00FF00FF00FF
    i64.and
    i64.const 8
    i64.shl
    i64.or
    local.set $x

    local.get $x
    i64.const 16
    i64.shr_u
    i64.const 0x0000FFFF0000FFFF
    i64.and
    local.get $x
    i64.const 0x0000FFFF0000FFFF
    i64.and
    i64.const 16
    i64.shl
    i64.or
    local.set $x

    local.get $x
    i64.const 32
    i64.shr_u
    local.get $x
    i64.const 32
    i64.shl
    i64.or
  )

  (func $reverseBits32 (param $x i32) (param $count i32) (result i32)
    local.get $x
    call $bitreverse32
    i32.const 32
    local.get $count
    i32.sub
    i32.shr_u
  )

  (func $reverseBits64 (param $x i64) (param $count i32) (result i64)
    local.get $x
    call $bitreverse64
    i64.const 64
    local.get $count
    i64.extend_i32_u
    i64.sub
    i64.shr_u
  )

  (func $reverseBitsByte (param $x i32) (param $count i32) (result i32)
    local.get $x
    i32.const 0xFF
    i32.and
    local.get $count
    call $reverseBits32
  )
  (export "reverseBitsByte" (func $reverseBitsByte))

  (func $reverseBitsShort (param $x i32) (param $count i32) (result i32)
    local.get $x
    i32.const 0xFFFF
    i32.and
    local.get $count
    call $reverseBits32
  )
  (export "reverseBitsShort" (func $reverseBitsShort))

  (func $reverseBitsInt (param $x i32) (param $count i32) (result i32)
    local.get $x
    local.get $count
    call $reverseBits32
  )
  (export "reverseBitsInt" (func $reverseBitsInt))

  (func $reverseBitsLong (param $x i64) (param $count i32) (result i64)
    local.get $x
    local.get $count
    call $reverseBits64
  )
  (export "reverseBitsLong" (func $reverseBitsLong))
)