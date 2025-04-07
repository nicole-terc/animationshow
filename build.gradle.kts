group "nstv.animationshow"
version "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
//    id("org.jetbrains.compose") apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
}


subprojects {
//    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
//        kotlinOptions {
//            freeCompilerArgs = freeCompilerArgs + listOf(
//                "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api",
//                "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi",
//            )
//        }
//    }
}
