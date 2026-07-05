## [Unreleased]

### Changed

- Updated to Gradle 9.6.1
- Updated to Karma Conventions 1.18.2
- Updated to NMCP 1.6.1

## [1.10.3]

### Fixed

- Intrinsics on Kotlin/JS breaking with minification enabled in production builds

## [1.10.2]

### Changed

- Performance improvements for any `Long` related intrinsics on Kotlin/JS
- Minor performance improvements for `BitSource`

## [1.10.1]

### Changed

- Updated to Gradle 9.6.0
- Updated to Karma Conventions 1.18.1
- Performance improvements for the `reverseBytes` intrinsics on Kotlin/Native
- Performance improvements for the `reverseBits` intrinsics on Kotlin/Native
- Performance improvements for the `reverseBytes` intrinsics on Kotlin/JS

## [1.10.0]

### Added

- WASM WASI support

### Changed

- Updated to Karma Conventions 1.18.0

## [1.9.0]

### Changed

- Updated to Kotlin 2.4.0
- Updated to Karma Conventions 1.17.0
- Downgraded to Gradle 9.4.1 because of IDEA compatibility regression

## [1.8.0]

### Added

- `BitSource.peekBits(Lsb)` function for per-bit lookahead
- `BitSource.requestBits` function for requesting n bits to be available
- `BitSource.requireBits` function for requiring at least n bits to be available
- `BitSource.peek<type>` extension functions for typed lookahead
- `BitSource.peek<type>Lsb` extension functions for typed LSB lookahead
- `BitSource.peek<type>Le` extension functions for typed LE lookahead
- `BitSource.peek<type>LeLsb` extension functions for typed LE LSB lookahead

## [1.7.0]

### Added

- `BitSink.reset` function for resetting bit and byte counts
- `BitSource.reset` function for resetting bit and byte counts

### Changed

- Optimized `BitSink` and `BitSource` implementations using buffered reading and bitwise operations
- Optimized `reverseBits` extensions on native targets using `__builtin_bitreverse` intrinsics
- Optimized `reverseBits` extensions on JVM targets using `Integer/Long.reverse` intrinsics

## [1.6.0]

### Added

- `BitSink.flush()` function for flushing internally buffered data on demand
- `Source` extensions for reading multibyte values in LE order using the optimized version of `reverseBytes`
- `Sink` extensions for writing multibyte values in LE order using the optimized version of `reverseBytes`

### Removed

- `BitSink.padUntilNextByte()` removed in favor of `BitSink.padToNextByte(UByte)`

## [1.5.0]

### Added

- `ByteArraySink` for accumulating data from a `Sink` into a regular `ByteArray`
- `ByteArraySource` for consuming a regular `ByteArray` as a `RawSource`

## [1.4.0]

### Added

- `BitOrder` enum for defining the default bit order of sinks and sources
- `bitOrder` parameter for `Source.bitSource()` and `Sink.bitSink()` extensions

## [1.3.0]

### Added

- LSB read- and write-extensions for `BitSource` and `BitSink`
- Little Endian LSB read- and write-extensions for `BitSource` and `BitSink`

## [1.2.0]

### Added

- `count` parameter for all `reverseBits` extensions with default value

## [1.1.0]

### Added

- `reverseBits()` extension for all signed integer types
- `reverseBits()` extension for all unsigned integer types

## [1.0.1]

### Changed

- Fixed potential bug in `BitSink` where the last byte is not flushed correctly

### Added

- More unit tests for padding, flushing and odd bit boundaries

## [1.0.0]

### Added

- `BitSource` interface for reading bit streams
- `BitSink` interface for writing bit streams
- Highly optimized `reverseBytes` extensions for all integral types for swapping endianess