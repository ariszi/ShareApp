plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    kotlin("android")
}

android {
    namespace = AppConfig.appId
    compileSdk = AppConfig.compileSdk
    buildToolsVersion = AppConfig.buildToolsVersion

    defaultConfig {
        applicationId = AppConfig.appId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_compiler
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(AppConfig.proguardDefaultFile),
                AppConfig.proguardConsumerRules
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.injectAssistedAnnotationDaggerLibrary)
    implementation(Libs.appCompactLibrary)
    implementation(Libs.coreKTXLibrary)
    implementation(Libs.vmSaveStateLibrary)
    implementation(Libs.lifecycleRuntimeKTXLibrary)
    implementation(Libs.jsrLibrary)
    implementation(Libs.kotlinxCoroutinesCoreLibrary)
    implementation(Libs.kotlinxCoroutinesAndroidLibrary)
    implementation(Libs.constraintLayoutLibrary)
    implementation(Libs.recycleViewLibrary)
    implementation(Libs.hiltAndroidLibrary)
    implementation(Libs.hiltFragmentNavigationLibrary)

    implementation(project(Module.featureOnboarding))
    implementation(project(Module.featurePinLogin))
    implementation(project(Module.featureMainScreen))
    implementation(project(Module.coreUi))
    implementation(project(Module.coreDatasource))

    kapt(Libs.hiltKaptCompilerLibrary)
    testImplementation(Libs.junitJupiterLibrary)
    testRuntimeOnly(Libs.junitJupiterEngineLibrary)


}
