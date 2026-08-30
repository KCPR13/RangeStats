plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kmpLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.android.lint)
}

kotlin {
    android {
        namespace = "pl.kacper.misterski.rangestats.feature.onboarding"
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

    val xcfName = "feature:onboardingKit"

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
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.bundles.android.test)
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
