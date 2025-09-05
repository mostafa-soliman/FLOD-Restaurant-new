plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)

    /** === CORE BEGIN === */
    alias(libs.plugins.kotlin.serialization)
    /** === CORE END === */
}

android {
    namespace = "com.fadali.flod_restaurant"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.fadali.flod_restaurant"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        buildConfig = true
    }
}
dependencies {

    // Android Core
    implementation(libs.bundles.android.core)
    
    // Compose BOM and UI
    implementation(project.dependencies.platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.bundles.compose.material)
    implementation(libs.bundles.compose.foundation)
    
    // Testing
    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.android.testing)
    androidTestImplementation(project.dependencies.platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.compose.testing)
    
    // Debug
    debugImplementation(libs.bundles.compose.debug)
    
    // Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    
    // Retrofit
    implementation(libs.bundles.retrofit)
    
    // Lifecycle
    implementation(libs.bundles.lifecycle)
    
    // Coil
    implementation(libs.bundles.coil)
    
    // Accompanist
    implementation(libs.bundles.accompanist)
    
    // Navigation
    implementation(libs.bundles.navigation)
    
    // DataStore
    implementation(libs.bundles.datastore)

    /** === CORE BEGIN === */
    implementation(project(":app:core"))
    implementation(libs.bundles.koin.android)
    /** === CORE END === */
}