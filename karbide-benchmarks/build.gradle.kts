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

@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import dev.karmakrafts.conventions.kotlin.defaultCompilerOptions
import dev.karmakrafts.conventions.kotlin.withBrowser
import dev.karmakrafts.conventions.kotlin.withJvm
import dev.karmakrafts.conventions.kotlin.withNative
import dev.karmakrafts.conventions.kotlin.withNodeJs
import dev.karmakrafts.conventions.kotlin.withWasmWasi
import dev.karmakrafts.conventions.kotlin.withWeb
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

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
        withBrowser {
            useEsModules()
        }
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
                implementation(libs.kotlinx.coroutines.core)
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
        named("main") {
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }
        create("reverseBytes") {
            include("dev.karmakrafts.karbide.bytes.*ReverseBytes*")
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }
        create("reverseBits") {
            include("dev.karmakrafts.karbide.bits.*ReverseBits*")
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }

        create("readSingleBits") {
            include("dev.karmakrafts.karbide.*ReadSingleBits*")
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }
        create("readMultipleBits") {
            include("dev.karmakrafts.karbide.*ReadMultipleBits*")
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }
        create("writeSingleBits") {
            include("dev.karmakrafts.karbide.*WriteSingleBits*")
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }
        create("writeMultipleBits") {
            include("dev.karmakrafts.karbide.*WriteMultipleBits*")
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }

        create("multipleBits") {
            include("dev.karmakrafts.karbide.*MultipleBits*")
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }
        create("singleBits") {
            include("dev.karmakrafts.karbide.*SingleBits*")
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }
    }
}