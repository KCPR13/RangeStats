plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.kmpLibrary)

}

kotlin {
    androidLibrary {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        namespace = "pl.kacper.misterski.rangestats.composeapp"
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
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.navigation.compose)
            implementation(libs.material.icons.extended)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.core)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
