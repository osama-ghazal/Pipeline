import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    // … any other plugs …
}

// 1. Load the “base” version props at configuration time
val versionPropsFile = rootProject.file("version.properties")
val versionProps = Properties().apply {
    load(FileInputStream(versionPropsFile))
}
val baseVersionName = versionProps.getProperty("VERSION_NAME")      // e.g. "1.0.29"
val baseVersionCode = versionProps.getProperty("VERSION_CODE").toInt() // e.g. 10029

android {
    compileSdk = 36
    buildToolsVersion = "36.0.0" // latest Build Tools (example)

    defaultConfig {
        applicationId = "com.example.pipleline"
        minSdk = 24
        targetSdk = 36

        // 2. For “release” builds, use exactly whatever’s in version.properties
        versionCode = baseVersionCode
        versionName = baseVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            // 3. Compute a dev‐only suffix at build time. E.g. “dev‐2306020945”
            val ts = System.currentTimeMillis() / 1000   // seconds since epoch
            versionCode = (baseVersionCode * 10_000) + (ts % 10_000).toInt()
            versionName = "${baseVersionName}-dev${ts}"

            // If you want an applicationIdSuffix or custom signing config, you can add here:
            // applicationIdSuffix = ".dev"
            // signingConfig = ...
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // signed APK config…
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

// Ensure KAPT and Kotlin compile use the same target
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
        javaParameters = true
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation(platform("androidx.compose:compose-bom:2024.1.0"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.firebase:firebase-firestore-ktx")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")

    // Retrofit / Gson / Logging
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // …etc…
}
