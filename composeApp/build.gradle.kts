plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.kmpLibrary)
}

kotlin {
    android {
        namespace = "pl.kacper.misterski.rangestats.composeapp"
        compileSdk {
            version = release(libs.versions.android.compileSdk.get().toInt()) {
                minorApiLevel = 1
            }
        }
        minSdk = libs.versions.android.minSdk.get().toInt()
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.bundles.koin.android)
        }
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.ui)
            implementation(projects.core.data)
            implementation(projects.core.navigation)
            implementation(projects.feature.session)
            implementation(projects.feature.history)
            implementation(projects.feature.ballistics)
            implementation(projects.feature.settings)
            implementation(projects.feature.onboarding)
            implementation(libs.bundles.compose)
            implementation(libs.bundles.lifecycle)
            implementation(libs.bundles.koin.compose)
            implementation(libs.navigation.compose)
            implementation(libs.material.icons.extended)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
