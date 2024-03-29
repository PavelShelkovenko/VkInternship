plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.shelkovenko.vkinternship"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.shelkovenko.vkinternship"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    // Coil
    implementation(libs.bundles.coil)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // Room
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    // Dagger
    implementation(libs.bundles.dagger)
    kapt(libs.dagger.compiler)
    kapt(libs.hilt.compiler)

    // Test
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.bundles.testing.ui)
    testImplementation(libs.bundles.testing)
    debugImplementation(libs.bundles.debug)
}