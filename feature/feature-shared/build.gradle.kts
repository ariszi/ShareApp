plugins {
    id("shareapp.android.library")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    api(project(Module.coreUi))
    api(project(Module.userRepository))
    api(libs.databinding)
    api(libs.fragment.ktx)
    api(libs.coroutines.core)
    api(libs.mockk)
    api(libs.kotest.property)
    api(libs.coroutines.testing)
    api(libs.turbine)
    api(libs.kotest.runner.junit5)
    api(libs.kotest.assertions.core)
}
android {
    namespace = "zi.aris.feature_shared"
}
