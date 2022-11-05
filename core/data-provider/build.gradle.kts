plugins {
    id("shareapp.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

dependencies {
    implementation(Library.dataStoreAndroidLibrary)
    implementation(Library.hiltAndroidLibrary)
    kapt(Library.hiltKaptCompilerLibrary)
    implementation(Library.injectAssistedAnnotationDaggerLibrary)
    api(Library.mockkLibrary)
    api(Library.coroutinesTestLibrary)
}
android {
    namespace = "zi.aris.data_provider"
}
