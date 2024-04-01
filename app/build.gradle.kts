plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.gmsService)
    alias(libs.plugins.kotlinKsp)
    alias(libs.plugins.hiltAndroid)
}

android {
    signingConfigs {
        create("release") {
            storeFile =
                file("C:\\Users\\jasmeet singh\\AndroidStudioProjects\\CineMate\\keystore.jks")
            storePassword = "jasmeet34"
            keyAlias = "release"
            keyPassword = "jasmeet34"
        }
    }
    namespace = "com.jasmeet.cinemate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jasmeet.cinemate"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        signingConfig = signingConfigs.getByName("release")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            multiDexEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.coil.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.firebase.bom))
    implementation (libs.firebase.firestore)
    implementation (libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation( libs.androidx.runtime.livedata )
    implementation(libs.googleFonts)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.lottie)
    implementation(libs.materialIcons)
    implementation(libs.hiltAndroid)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation(libs.room)
    implementation(libs.roomKtx)
    implementation(libs.androidx.material3.window.size)
    implementation (libs.firebase.analytics.ktx)


    ksp(libs.hiltCompiler)
    ksp(libs.hiltCompilerKapt)
    ksp(libs.roomCompiler)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)




}