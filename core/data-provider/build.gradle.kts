plugins {
    id("android-common-build")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

dependencies {
    implementation(Libs.dataStoreAndroidLibrary)
    implementation(Libs.hiltAndroidLibrary)
    kapt(Libs.hiltKaptCompilerLibrary)
    implementation(Libs.injectAssistedAnnotationDaggerLibrary)
    api(Libs.mockkLibrary)
    api(Libs.coroutinesTestLibrary)
}
