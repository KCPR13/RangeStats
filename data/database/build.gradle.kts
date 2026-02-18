@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kmpLibrary)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.ksp)
}

kotlin {
    androidLibrary {
        namespace = "pl.kacper.misterski.rangestats.data.database"
        compileSdk {
            version = release(libs.versions.android.compileSdk.get().toInt())
        }
        minSdk = libs.versions.android.minSdk.get().toInt()

        withHostTestBuilder {
        }

        androidResources {
            enable = true
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    val xcfName = "data:databaseKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.androidx.room.runtime)
                implementation(libs.androidx.sqlite.bundled)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.room.sqlite.wrapper)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.testExt.junit)
                implementation(libs.androidx.room.testing)
            }
        }

        iosMain {
            dependencies {

            }
        }
    }
}

dependencies {
    "kspAndroid"(libs.androidx.room.compiler)
    "kspIosSimulatorArm64"(libs.androidx.room.compiler)
    "kspIosX64"(libs.androidx.room.compiler)
    "kspIosArm64"(libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}
