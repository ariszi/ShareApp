plugins {
    id("android-common-build")
}

android {

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_material
    }
}

dependencies {
    api(project(Module.coreUi))
}
