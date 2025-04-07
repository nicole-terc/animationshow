plugins {
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.composeCompiler)
}



group "nstv.animationshow"
version "1.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation(libs.androidx.activity.compose)
}

android {
    namespace = "nstv.animationshow.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "nstv.animationshow.android"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "1.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
