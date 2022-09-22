plugins {
    id("android-common-build")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

dependencies {
    implementation(Libs.dataStoreAndroidLibrary)
    implementation(Libs.hiltAndroidLibrary)
    implementation(Libs.hiltKaptCompilerLibrary)
}
