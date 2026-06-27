# Karbide Benchmarks

You can easily obtain the performance graphs yourself by running the included `benchmarks.ipynb`  
Kotlin notebook file.

All benchmark results shown in this README have been run on the following hardware:
- AMD Threadripper 1950X 16C/32T
- 64GB DDR4-3200
- PopOS 24.04 (Linux 7.0.11-76070011-generic)

## Bitops

### `reverseBytes`

<p>
    <img src="../docs/reverseBytes_jvm.png" alt="Performance graph for reverseBytes function on JVM" width="45%"/>
    <img src="../docs/reverseBytes_linuxX64.png" alt="Performance graph for reverseBytes function on Native" width="45%"/>
</p>
<p>
    <img src="../docs/reverseBytes_js.png" alt="Performance graph for reverseBytes function on JS" width="45%"/>
    <img src="../docs/reverseBytes_wasmJs.png" alt="Performance graph for reverseBytes function on WASM/JS" width="45%"/>
</p>

---

### `reverseBits`

<p>
    <img src="../docs/reverseBits_jvm.png" alt="Performance graph for reverseBytes function on JVM" width="45%"/>
    <img src="../docs/reverseBits_linuxX64.png" alt="Performance graph for reverseBytes function on Native" width="45%"/>
</p>
<p>
    <img src="../docs/reverseBits_js.png" alt="Performance graph for reverseBytes function on JS" width="45%"/>
    <img src="../docs/reverseBits_wasmJs.png" alt="Performance graph for reverseBytes function on WASM/JS" width="45%"/>
</p>