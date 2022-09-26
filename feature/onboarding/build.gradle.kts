plugins{
    id("android-common-build")
    id("androidx.navigation.safeargs")
}

android {

    buildFeatures {
        viewBinding = true
    }
}

dependencies{
    api(project(Module.featureShared))
}
