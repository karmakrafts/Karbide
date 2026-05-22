## [Unreleased]

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