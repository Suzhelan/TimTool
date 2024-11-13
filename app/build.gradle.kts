plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "top.sacz.timtool"
    compileSdk = 35

    defaultConfig {
        applicationId = "top.sacz.timtool"
        minSdk = 26
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //xposed
    compileOnly(libs.xposed.api)
    implementation(libs.ezx.helper)
    implementation(libs.dexkit)

    //常用
    implementation(libs.okhttp3)
    implementation(libs.glide)
    implementation(libs.fastkv)
    implementation(libs.fastjson2)
    implementation(libs.base.recyclerview.helper)
}