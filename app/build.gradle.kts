import java.util.Properties
import java.io.FileInputStream

// … your versionProps loading code, etc. …

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    // removed: id("com.google.gms.google-services")
}

val versionPropsFile = rootProject.file("version.properties")
val versionProps = Properties().apply {
    load(FileInputStream(versionPropsFile))
}

android {
    compileSdk = 36
    buildToolsVersion = "20.0.0" // you can remove this entirely if you wish (AGP will pick a default)
    namespace = "com.example.pipleline"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    defaultConfig {
        applicationId = "com.example.pipleline"
        minSdk = 24
        targetSdk = 36
        versionCode = versionProps.getProperty("VERSION_CODE").toInt()
        versionName = versionProps.getProperty("VERSION_NAME")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

// Force KotlinCompile & KAPT to use JVM 11
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
        javaParameters = true
    }
}

dependencies {
    // Core Android + Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Firestore
    implementation(libs.firebase.firestore.ktx)

    debugImplementation(libs.androidx.ui.tooling)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
}

//––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
// Only apply the Google-Services plugin if google-services.json actually exists.
//––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
val gmsJson = rootProject.file("app/google-services.json")
if (gmsJson.exists()) {
    apply(plugin = "com.google.gms.google-services")
}
