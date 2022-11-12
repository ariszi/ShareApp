plugins {
    id("shareapp.android.library")
    id("androidx.navigation.safeargs")
}

android {

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.compose_compiler
    }
    namespace = "zi.aris.ui"
}

dependencies {
    api(libs.android.material)
    api(libs.compose.material)
    api(libs.compose.ui)
    api(libs.compose.ui.tooling)
    api(libs.compose.foundation)
    api(libs.compose.compiler)
    api(libs.navigation.fragment.ktx)
    api(libs.navigation.ui)
    api(libs.navigation.compose)
}
