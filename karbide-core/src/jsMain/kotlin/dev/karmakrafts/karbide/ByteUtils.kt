package dev.karmakrafts.karbide

/**
 * These intrinsics are handwritten inline JS to force a more strict ASM.JS coercion.
 */

@Suppress("ThisExpressionReferencesGlobalObjectJS")
actual fun Short.reverseBytes(): Short = js(
    """
        (function(x) {
            x = x | 0;
            return ((((x << 8) & 0xFF00) | ((x >>> 8) & 0xFF)) << 16) >> 16;
        })(this)        
    """
)

@Suppress("ThisExpressionReferencesGlobalObjectJS")
actual fun Int.reverseBytes(): Int = js(
    """
        (function(x) {
            x = x | 0;
            return (((x & 0x000000FF) << 24) | ((x & 0x0000FF00) << 8) | ((x >>> 8) & 0x0000FF00) | ((x >>> 24) & 0x000000FF)) | 0;
        })(this)
    """
)

@Suppress("ThisExpressionReferencesGlobalObjectJS")
actual fun Long.reverseBytes(): Long = js(
    """
        (function(x) {
            x = x | 0;
            const lo = x >>> 0;
            const hi = (x / 4294967296) | 0;
            const swap = function(v) {
                return (
                    ((v & 0xFF) << 24) |
                    ((v & 0xFF00) << 8) |
                    ((v >>> 8) & 0xFF00) |
                    ((v >>> 24) & 0xFF)
                ) | 0;
            };
            const lo2 = swap(lo);
            const hi2 = swap(hi);
            return (lo2 * 4294967296 + (hi2 >>> 0)) | 0;
        })(this)
    """
)