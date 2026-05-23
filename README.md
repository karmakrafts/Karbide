# Karbide

[![](https://git.karmakrafts.dev/kk/karbide/badges/master/pipeline.svg)](https://git.karmakrafts.dev/kk/karbide/-/pipelines)
[![](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo.maven.apache.org%2Fmaven2%2Fdev%2Fkarmakrafts%2Fkarbide%2Fkarbide-core%2Fmaven-metadata.xml
)](https://git.karmakrafts.dev/kk/karbide/-/packages)
[![](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fcentral.sonatype.com%2Frepository%2Fmaven-snapshots%2Fdev%2Fkarmakrafts%2Fkarbide%2Fkarbide-core%2Fmaven-metadata.xml
)](https://git.karmakrafts.dev/kk/karbide/-/packages)
[![](https://img.shields.io/badge/2.3.21-blue?logo=kotlin&label=kotlin)](https://kotlinlang.org/)
[![](https://img.shields.io/badge/documentation-black?logo=kotlin)](https://docs.karmakrafts.dev/karbide-core)

This library allows treating any kotlinx.io `Source` or `Sink` as a bit stream!

It introduces `BitSink` and `BitSource` interfaces, which can be obtained simply
by calling `bitSink()` or `bitSource()` on any `Sink` or `Source`,
which allow reading and writing data in increments smaller than a byte.

It also provides various extensions and utilities for working with bits and bytes.

### How to use it

First, add the official Maven Central repository to your settings.gradle.kts:

```kotlin
dependencyResolutionManagement {
    repositories {
        maven("https://central.sonatype.com/repository/maven-snapshots")
        mavenCentral()
    }
}
```

Then add a dependency on the library in your root buildscript:

```kotlin
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation("dev.karmakrafts.karbide:karbide-core:<version>")
            }
        }
    }
}
```

Or, if you are only using Kotlin/JVM, add it to your top-level dependencies block instead.