plugins {
    id("android-common-build")
    id("androidx.navigation.safeargs")
}

android {

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_compiler
    }
}

dependencies {
    api(Libs.materialComponentsLibrary)
    api(Libs.composeMaterialLibrary)
    api(Libs.composeUiLibrary)
    api(Libs.composeToolingLibrary)
    api(Libs.composeFoundationLibrary)
    api(Libs.composeCompilerLibrary)
    implementation(Libs.navigationLibrary)
    implementation(Libs.navigationKTXLibrary)
    implementation(Libs.navigationComposeLibrary)
    implementation(Libs.glideLibrary)
    implementation(Libs.lottieLibrary)
}
