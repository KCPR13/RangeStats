plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kmpLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.android.lint)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "pl.kacper.misterski.rangestats.core.ui"
}

kotlin {
    android {
        namespace = "pl.kacper.misterski.rangestats.core.ui"
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

    val xcfName = "core:uiKit"

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
                implementation(libs.bundles.compose)
                implementation(libs.bundles.lifecycle)
                implementation(libs.material.icons.extended)
                implementation(libs.navigation.compose)
                implementation(libs.compottie)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
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
