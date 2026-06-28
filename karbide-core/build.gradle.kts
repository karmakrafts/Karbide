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

@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalUnsignedTypes::class)

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
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

buildscript {
    dependencies {
        classpath(libs.kotlinPoet)
    }
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
        wasmJsMain {
            kotlin {
                srcDir(project.layout.buildDirectory.file("generated/wasmJsIntrinsics"))
            }
            dependencies {
                implementation(libs.kotlin.wrappers.browser)
            }
        }
    }
}

tasks {
    withType<KotlinJvmTest>().configureEach {
        jvmArgs("-Xms2G", "-Xmx2G")
    }
    val compileWasmJsIntrinsics = register<Exec>("compileWasmJsIntrinsics") {
        group = "intrinsics"
        description = "Compile WASM/JS intrinsics WAT to a WASM binary"
        workingDir = project.file("src/wasmJsMain/wat")
        val outputDir = project.layout.buildDirectory.dir("wat2wasm")
        val outputFile = outputDir.get().file("karbide_intrinsics.wasm").asFile
        inputs.file(project.file("src/wasmJsMain/wat/karbide_intrinsics.wat"))
        outputs.file(outputFile)
        doFirst {
            outputs.files.singleFile.ensureParentDirsCreated()
        }
        commandLine("wat2wasm", "karbide_intrinsics.wat", "-o", outputFile.absolutePath)
        onlyIf { // @formatter:off
            val checker = if(Os.isFamily(Os.FAMILY_WINDOWS)) "where" else "which"
            ProcessBuilder()
                .command(checker, "wat2wasm")
                .apply { environment.putAll(System.getenv()) }
                .start()
                .waitFor() == 0
        } // @formatter:on
    }
    val generateWasmJsIntrinsicsBlob = register("generateWasmJsIntrinsicsBlob") {
        dependsOn(compileWasmJsIntrinsics)
        group = "intrinsics"
        description = "Generate a Kotlin file with the embedded WASM/JS binary blob"
        val inputDir = project.layout.buildDirectory.dir("wat2wasm")
        val inputFile = inputDir.get().file("karbide_intrinsics.wasm").asFile
        val outputDir = project.layout.buildDirectory.dir("generated/wasmJsIntrinsics")
        inputs.file(inputFile)
        doLast {
            val data = inputs.files.singleFile.readBytes()
            val formattedData = data.joinToString(", ") { byte -> "0x${byte.toHexString().uppercase()}U" }
            // @formatter:off
            val file = FileSpec.builder("dev.karmakrafts.karbide", "KarbideIntrinsicsBlob")
                .addProperty(PropertySpec.builder("karbideIntrinsicsBlob", UByteArray::class, KModifier.INTERNAL)
                    .initializer("ubyteArrayOf($formattedData)")
                    .build())
                .build()
            // @formatter:on
            val outputFile = outputDir.get().asFile
            file.writeTo(outputFile)
        }
    }
    named("prepareKotlinIdeaImport") {
        dependsOn(generateWasmJsIntrinsicsBlob)
    }
    named("compileKotlinWasmJs") {
        dependsOn(generateWasmJsIntrinsicsBlob)
    }
    named("compileKotlinWasmWasi") {
        dependsOn(generateWasmJsIntrinsicsBlob)
    }
}

publishing {
    setProjectInfo(
        name = "Karbide Core",
        description = "Bit manipulation, readers and writers for kotlinx.io.",
        url = "https://git.karmakrafts.dev/kk/karbide"
    )
}