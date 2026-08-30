import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
    alias(libs.plugins.kmpLibrary) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.testBalloon) apply false
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    extensions.configure<DetektExtension> {
        buildUponDefaultConfig = true
        parallel = true
        config.setFrom(rootProject.file("config/detekt/detekt.yml"))
    }

    extensions.configure<KtlintExtension> {
        filter {
            exclude { it.file.path.replace('\\', '/').contains("/build/") }
        }
    }

    afterEvaluate {
        val kotlinMultiplatform = extensions.findByType<KotlinMultiplatformExtension>()
        if (kotlinMultiplatform != null) {
            tasks.withType<Detekt>().configureEach {
                setSource(
                    kotlinMultiplatform.sourceSets.flatMap { it.kotlin.srcDirs }
                        .filterNot { it.path.contains("${File.separator}build${File.separator}") },
                )
            }
        }
    }
}