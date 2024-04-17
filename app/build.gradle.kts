plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.gmsService)
    alias(libs.plugins.kotlinKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.secrets.plugin)
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
            isDebuggable = true
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
        buildConfig = true
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

    //viewmodel and lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation( libs.androidx.runtime.livedata )
    implementation(libs.androidx.material3.window.size)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation (libs.firebase.firestore)
    implementation (libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation (libs.firebase.analytics.ktx)
    implementation(libs.play.services.tagmanager)

    //Ui
    implementation(libs.googleFonts)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.lottie)
    implementation(libs.materialIcons)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.toast.magic)

    //Network
    implementation (libs.landscapist.bom)
    implementation (libs.landscapist.coil)
    implementation (libs.landscapist.placeholder)
    implementation (libs.landscapist.animation)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.moshi.kotlin)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)
    implementation(libs.coil.compose)

    //hilt
    implementation(libs.hiltAndroid)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.palette.ktx)
    ksp(libs.hiltCompiler)
    ksp(libs.hiltCompilerKapt)

    //room
    implementation(libs.room)
    implementation(libs.roomKtx)
    ksp(libs.roomCompiler)

    //yt player view
    implementation (libs.ytPlayerView)

    //datastore
    implementation(libs.androidx.datastore.preferences)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    
}