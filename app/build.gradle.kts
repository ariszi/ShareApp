plugins {
    id("shareapp.android.application")
    id("androidx.navigation.safeargs")
    id("shareapp.di")
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
        kotlinCompilerExtensionVersion = AppConfig.compose_compiler
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

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.app.compat)
    implementation(libs.core.ktx)
    implementation(libs.saved.state)
    implementation(libs.lifecycle)
    implementation(libs.jsr250)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.constraint.layout)

    implementation(project(Module.featureOnboarding))
    implementation(project(Module.featurePinLogin))
    implementation(project(Module.featureMainScreen))
    implementation(project(Module.coreUi))
    implementation(project(Module.userRepository))

}
