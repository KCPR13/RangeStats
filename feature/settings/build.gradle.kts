plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kmpLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.testBalloon)
}

kotlin {
    android {
        namespace = "pl.kacper.misterski.rangestats.feature.settings"
        compileSdk {
            version =
                release(
                    libs.versions.android.compileSdk
                        .get()
                        .toInt(),
                )
        }
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    val xcfName = "feature:settingsKit"

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
                implementation(projects.core.domain)
                implementation(projects.core.ui)
                implementation(projects.core.data)
                implementation(projects.core.navigation)
                implementation(libs.bundles.compose)
                implementation(libs.bundles.lifecycle)
                implementation(libs.bundles.koin.compose)
                implementation(libs.navigation.compose)
                implementation(libs.material.icons.extended)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.testBalloon.framework.core)
                implementation(libs.kotlinx.coroutines.test)
                implementation(projects.core.testing)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.bundles.koin.android)
            }
        }

        getByName("androidHostTest") {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.robolectric)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.bundles.android.test)
                implementation(libs.testBalloon.framework.core)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}
