plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = AppConfig.appId
    compileSdk = AppConfig.compileSdk
    buildToolsVersion = AppConfig.buildToolsVersion

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}
