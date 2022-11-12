plugins {
    id("shareapp.android.library")
    id("shareapp.di")
}

dependencies {
    implementation(libs.datastore.preferences)
    api(libs.mockk)
    api(libs.coroutines.testing)
}
android {
    namespace = "zi.aris.data_provider"
}
