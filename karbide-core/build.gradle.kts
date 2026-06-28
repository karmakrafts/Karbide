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

import dev.karmakrafts.conventions.configureJava
import dev.karmakrafts.conventions.dokka.configureDokka
import dev.karmakrafts.conventions.kotlin.defaultCompilerOptions
import dev.karmakrafts.conventions.kotlin.withAndroidLibrary
import dev.karmakrafts.conventions.kotlin.withBrowser
import dev.karmakrafts.conventions.kotlin.withJvm
import dev.karmakrafts.conventions.kotlin.withNative
import dev.karmakrafts.conventions.kotlin.withNodeJs
import dev.karmakrafts.conventions.kotlin.withWasmWasi
import dev.karmakrafts.conventions.kotlin.withWeb
import dev.karmakrafts.conventions.setProjectInfo
import org.apache.tools.ant.taskdefs.condition.Os
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    signing
    `maven-publish`
}

configureJava(libs.versions.java)

configureDokka {
    withKotlin()
    withKotlinxIo()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
    defaultCompilerOptions()
    withSourcesJar()
    withAndroidLibrary("$group.core")
    withNative {
        compilations {
            named("main") {
                cinterops {
                    create("builtins")
                }
            }
        }
    }
    withJvm()
    withWeb {
        withBrowser {
            useEsModules()
        }
        withNodeJs()
    }
    withWasmWasi {
        withNodeJs()
    }
    applyDefaultHierarchyTemplate {
        common {
            group("jvmAndAndroid") {
                withJvm()
                withAndroidLibrary()
            }
            group("wasmJsAndWasi") {
                withWasmJs()
                withWasmWasi()
            }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.io.bytestring)
                api(libs.kotlinx.io.core)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

tasks {
    withType<KotlinJvmTest>().configureEach {
        jvmArgs("-Xms2G", "-Xmx2G")
    }
    val compileWasmIntrinsics = register<Exec>("compileWasmIntrinsics") {
        group = "wasm"
        description = "Compile WASM intrinsics WAT to a WASM binary"
        workingDir = project.file("src/wasmJsAndWasiMain/wat")
        val outputDir = project.layout.buildDirectory.dir("wat2wasm")
        val outputFile = outputDir.get().file("intrinsics.wasm").asFile
        outputFile.ensureParentDirsCreated()
        commandLine("wat2wasm", "intrinsics.wat", "-o", outputFile.absolutePath)
        onlyIf { // @formatter:off
            val checker = if(Os.isFamily(Os.FAMILY_WINDOWS)) "where" else "which"
            ProcessBuilder()
                .command(checker, "wat2wasm")
                .apply { environment.putAll(System.getenv()) }
                .start()
                .waitFor() == 0
        } // @formatter:on
    }
    val generateWasmIntrinsicsBlob = register("generateWasmIntrinsicsBlob") {
        dependsOn(compileWasmIntrinsics)
        group = "wasm"
        description = "Generate a Kotlin file with the embedded WASM binary blob"
    }
    named("compileKotlinWasmJs") {
        dependsOn(compileWasmIntrinsics)
    }
    named("compileKotlinWasmWasi") {
        dependsOn(compileWasmIntrinsics)
    }
}

publishing {
    setProjectInfo(
        name = "Karbide Core",
        description = "Bit manipulation, readers and writers for kotlinx.io.",
        url = "https://git.karmakrafts.dev/kk/karbide"
    )
}