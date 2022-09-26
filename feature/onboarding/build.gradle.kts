plugins{
    id("android-common-build")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {

    buildFeatures {
        viewBinding = true
    }
}

dependencies{
    api(project(Module.featureShared))
    implementation(Libs.hiltAndroidLibrary)
    implementation(Libs.hiltFragmentNavigationLibrary)
    kapt(Libs.hiltKaptCompilerLibrary)
}
