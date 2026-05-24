## [Unreleased]

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