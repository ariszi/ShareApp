plugins {
    id("android-common-build")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    api(project(Module.coreUi))
    api(project(Module.coreDatasource))
    api(Libs.dataBindingLibrary)
    api(Libs.fragmentKTXLibrary)
    api(Libs.mockkLibrary)
    api(Libs.kotlinxCoroutinesCoreLibrary)
    api(Libs.coroutinesTestLibrary)
    api(Libs.turbineTestLibrary)
    api(Libs.kotestRunnerLibrary)
    api(Libs.kotestAssertionsLibrary)
    api(Libs.kotestPropertyLibrary)
}
