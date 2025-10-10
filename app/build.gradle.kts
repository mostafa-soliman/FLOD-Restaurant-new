plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
    // App Core
    alias(libs.plugins.core.network)
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
        
        buildConfigField("String", "BASE_URL", "\"https://api.fodek.net/\"")
        buildConfigField("String", "RESOURCES_BASE_URL", "\"https://feerotech.blob.core.windows.net/public/\"")
        buildConfigField("String", "TENANT_ID", "\"0199bda7-ba1c-7d79-8a56-350f00d6f4ba\"")
        buildConfigField("String", "SUB_DOMAIN", "\"Https://astrecode.dev.fodek.net\"")
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
        create("staging") {
            initWith(getByName("release"))
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
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

    // App Core
    implementation(libs.bundles.app.core)
    
    // Security
    implementation(libs.bundles.security)
    
    // Kotlin Reflection
    implementation(libs.kotlin.reflect)

    // Media3
    implementation(libs.bundles.media3)
}
