# Benchmarks

You can easily obtain the performance graphs yourself by running the included `benchmarks.ipynb`  
Kotlin notebook file.

All benchmark results shown in this README have been run on the following hardware:

- AMD Threadripper 1950X 16C/32T
- 64GB DDR4-3200
- PopOS 24.04 (Linux 7.0.11-76070011-generic)

The Kotlin version as of the last update of this file is **2.4.10**.

## Bitops

These benchmarks compare Karbide bitops against their common Kotlin counterparts.

### `reverseBytes`

<p>
    <img src="docs/reverseBytes_jvm.png" alt="Performance graph for reverseBytes function on JVM" width="45%"/>
    <img src="docs/reverseBytes_linuxX64.png" alt="Performance graph for reverseBytes function on Native" width="45%"/>
</p>
<p>
    <img src="docs/reverseBytes_js.png" alt="Performance graph for reverseBytes function on JS" width="45%"/>
    <img src="docs/reverseBytes_wasmJs.png" alt="Performance graph for reverseBytes function on WASM/JS" width="45%"/>
</p>

---

### `reverseBits`

<p>
    <img src="docs/reverseBits_jvm.png" alt="Performance graph for reverseBytes function on JVM" width="45%"/>
    <img src="docs/reverseBits_linuxX64.png" alt="Performance graph for reverseBytes function on Native" width="45%"/>
</p>
<p>
    <img src="docs/reverseBits_js.png" alt="Performance graph for reverseBytes function on JS" width="45%"/>
    <img src="docs/reverseBits_wasmJs.png" alt="Performance graph for reverseBytes function on WASM/JS" width="45%"/>
</p>

---

## `BitSource` and `BitSink`

These benchmarks compare Karbide `BitSource` and `BitSink` against a pure Kotlin inline implementation optimized
for the corresponding use case.

### `readBit`/`writeBit`

<p>
    <img src="docs/singleBits_jvm.png" alt="Performance graph for readBit and writeBit functions on JVM" width="45%"/>
    <img src="docs/singleBits_linuxX64.png" alt="Performance graph for readBit and writeBit functions on Native" width="45%"/>
</p>
<p>
    <img src="docs/singleBits_js.png" alt="Performance graph for readBit and writeBit functions on JS" width="45%"/>
    <img src="docs/singleBits_wasmJs.png" alt="Performance graph for readBit and writeBit functions on WASM/JS" width="45%"/>
</p>

### `readBits`/`writeBits`

<p>
    <img src="docs/multipleBits_jvm.png" alt="Performance graph for readBits and writeBits functions on JVM" width="45%"/>
    <img src="docs/multipleBits_linuxX64.png" alt="Performance graph for readBits and writeBits functions on Native" width="45%"/>
</p>
<p>
    <img src="docs/multipleBits_js.png" alt="Performance graph for readBits and writeBits functions on JS" width="45%"/>
    <img src="docs/multipleBits_wasmJs.png" alt="Performance graph for readBits and writeBits functions on WASM/JS" width="45%"/>
</p>