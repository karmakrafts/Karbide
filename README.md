# Karbide

This library allows treating any kotlinx.io `Source` or `Sink` as a bit stream!

It introduces `BitWriter` and `BitReader` interfaces, which can be obtained simply
by calling `bitWriter()` or `bitReader()` on any `Sink` or `Source`.
Custom implementation may also be used.

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