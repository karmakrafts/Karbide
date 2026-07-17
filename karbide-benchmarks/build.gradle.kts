/*
 * Copyright 2026 Karma Krafts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import dev.karmakrafts.conventions.kotlin.defaultCompilerOptions
import dev.karmakrafts.conventions.kotlin.withBrowser
import dev.karmakrafts.conventions.kotlin.withJvm
import dev.karmakrafts.conventions.kotlin.withNative
import dev.karmakrafts.conventions.kotlin.withNodeJs
import dev.karmakrafts.conventions.kotlin.withWasmWasi
import dev.karmakrafts.conventions.kotlin.withWeb
import kotlinx.benchmark.gradle.BenchmarkConfiguration

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.benchmark)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
    defaultCompilerOptions()
    withJvm()
    withNative()
    withWeb {
        compilerOptions {
            target = "es2015"
        }
        useEsModules()
        withBrowser()
        withNodeJs()
    }
    withWasmWasi {
        withNodeJs()
    }
    applyDefaultHierarchyTemplate()
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.karbideCore)
                implementation(libs.kotlinx.benchmark.runtime)
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.jmh.core)
            }
        }
    }
}

benchmark {
    targets {
        register("jvm")
        register("linuxX64")
        register("linuxArm64")
        register("mingwX64")
        register("js")
        register("wasmJs")
        register("wasmWasi")
    }
    configurations {
        fun BenchmarkConfiguration.defaultConfig() {
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }
        named("main") {
            defaultConfig()
        }
        create("reverseBytes") {
            include("dev.karmakrafts.karbide.bytes.*ReverseBytes*")
            defaultConfig()
        }
        create("reverseBits") {
            include("dev.karmakrafts.karbide.bits.*ReverseBits*")
            defaultConfig()
        }
        create("readSingleBits") {
            include("dev.karmakrafts.karbide.*ReadSingleBits*")
            defaultConfig()
        }
        create("readMultipleBits") {
            include("dev.karmakrafts.karbide.*ReadMultipleBits*")
            defaultConfig()
        }
        create("writeSingleBits") {
            include("dev.karmakrafts.karbide.*WriteSingleBits*")
            defaultConfig()
        }
        create("writeMultipleBits") {
            include("dev.karmakrafts.karbide.*WriteMultipleBits*")
            defaultConfig()
        }
        create("multipleBits") {
            include("dev.karmakrafts.karbide.*MultipleBits*")
            defaultConfig()
        }
        create("singleBits") {
            include("dev.karmakrafts.karbide.*SingleBits*")
            defaultConfig()
        }
    }
}