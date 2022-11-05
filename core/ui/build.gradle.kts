plugins {
    id("shareapp.android.library")
    id("androidx.navigation.safeargs")
}

android {

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_compiler
    }
    namespace = "zi.aris.ui"
}

dependencies {
    api(Library.materialComponentsLibrary)
    api(Library.composeMaterialLibrary)
    api(Library.composeUiLibrary)
    api(Library.composeToolingLibrary)
    api(Library.composeFoundationLibrary)
    api(Library.composeCompilerLibrary)
    api(Library.navigationLibrary)
    api(Library.navigationKTXLibrary)
    api(Library.navigationComposeLibrary)

    implementation(Library.materialLibrary)
}
