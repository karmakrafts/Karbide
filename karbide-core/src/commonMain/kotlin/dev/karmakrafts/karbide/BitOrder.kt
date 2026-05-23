package dev.karmakrafts.karbide

/**
 * In which order the bits of each byte are read/written from/to a source/sink.
 */
enum class BitOrder {
    /**
     * Most significant bit first.
     * Reading/writing binary from left to right.
     */
    MSB_FIRST,

    /**
     * Least significant bit first.
     * Reading/writing binary from right to left.
     */
    LSB_FIRST
}