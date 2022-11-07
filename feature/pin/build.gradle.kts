plugins {
    id("shareapp.android.library")
    id("androidx.navigation.safeargs")
    id("shareapp.di")
}

android {

    buildFeatures {
        viewBinding = true
    }
    namespace = "zi.aris.pin"
}

dependencies {
    api(project(Module.featureShared))
}
