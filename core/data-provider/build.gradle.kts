plugins {
    id("android-common-build")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {
    implementation(Libs.dataStoreAndroidLibrary)
    implementation(Libs.hiltAndroidLibrary)
    implementation(Libs.hiltKaptCompilerLibrary)
}
