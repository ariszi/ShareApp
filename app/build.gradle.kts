plugins {
    id("shareapp.android.application")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
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
    packagingOptions {
        resources.excludes.add("META-INF/**")
        resources.excludes.add("**/attach_hotspot_windows.dll")
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
    implementation(Library.injectAssistedAnnotationDaggerLibrary)
    implementation(Library.appCompactLibrary)
    implementation(Library.coreKTXLibrary)
    implementation(Library.vmSaveStateLibrary)
    implementation(Library.lifecycleRuntimeKTXLibrary)
    implementation(Library.jsrLibrary)
    implementation(Library.kotlinxCoroutinesCoreLibrary)
    implementation(Library.kotlinxCoroutinesAndroidLibrary)
    implementation(Library.constraintLayoutLibrary)
    implementation(Library.recycleViewLibrary)
    implementation(Library.hiltAndroidLibrary)
    implementation(Library.hiltFragmentNavigationLibrary)

    implementation(project(Module.featureOnboarding))
    implementation(project(Module.featurePinLogin))
    implementation(project(Module.featureMainScreen))
    implementation(project(Module.coreUi))
    implementation(project(Module.coreDatasource))

    kapt(Library.hiltKaptCompilerLibrary)
    testImplementation(Library.junitJupiterLibrary)
    testRuntimeOnly(Library.junitJupiterEngineLibrary)


}
