plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        register("kotlinModule") {
            id = "convention.kotlin-module"
            implementationClass = "KotlinModulePlugin"
        }
        register("coreModule") {
            id = "convention.core-module"
            implementationClass = "CoreModulePlugin"
        }
        register("networkModule") {
            id = "convention.network-module"
            implementationClass = "NetworkModulePlugin"
        }
    }
}

dependencies {
    // access version catalog properly
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.kotlin.serialization.gradlePlugin)
}
