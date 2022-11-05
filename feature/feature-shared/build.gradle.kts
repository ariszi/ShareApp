plugins {
    id("shareapp.android.library")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    api(project(Module.coreUi))
    api(project(Module.coreDatasource))
    api(Library.dataBindingLibrary)
    api(Library.fragmentKTXLibrary)
    api(Library.mockkLibrary)
    api(Library.kotlinxCoroutinesCoreLibrary)
    api(Library.coroutinesTestLibrary)
    api(Library.turbineTestLibrary)
    api(Library.kotestRunnerLibrary)
    api(Library.kotestAssertionsLibrary)
    api(Library.kotestPropertyLibrary)
}
android {
    namespace = "zi.aris.feature_shared"
}
