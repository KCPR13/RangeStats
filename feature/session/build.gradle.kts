plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kmpLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.android.lint)
}

kotlin {
    android {
        namespace = "pl.kacper.misterski.rangestats.feature.session"
        compileSdk {
            version = release(libs.versions.android.compileSdk.get().toInt()) {
                minorApiLevel = 1
            }
        }
        minSdk = libs.versions.android.minSdk.get().toInt()

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    val xcfName = "feature:sessionKit"

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
                implementation(projects.core.domain)
                implementation(projects.core.ui)
                implementation(projects.core.data)
                implementation(libs.bundles.compose)
                implementation(libs.bundles.lifecycle)
                implementation(libs.bundles.koin.compose)
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
