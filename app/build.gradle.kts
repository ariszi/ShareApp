plugins {
    id("shareapp.android.application")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = AppConfig.appId

    defaultConfig {
        applicationId = AppConfig.appId
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
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
