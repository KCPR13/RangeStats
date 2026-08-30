plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kmpLibrary)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

room {
    schemaDirectory("$projectDir/schemas")
}

kotlin {
    android {
        namespace = "pl.kacper.misterski.rangestats.core.data"
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

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    val xcfName = "core:dataKit"

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
                implementation(libs.bundles.room)
                implementation(libs.bundles.ktor)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.datastore.preferences.core)
                implementation(libs.koin.core)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.androidx.core.ktx)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.bundles.android.test)
                implementation(libs.androidx.room.testing)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
    }
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
}
