plugins{
    id("shareapp.android.library")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {

    buildFeatures {
        viewBinding = true
    }
    namespace = "zi.aris.onboarding"
}

dependencies{
    api(project(Module.featureShared))
    implementation(Library.hiltAndroidLibrary)
    implementation(Library.hiltFragmentNavigationLibrary)
    kapt(Library.hiltKaptCompilerLibrary)
}
