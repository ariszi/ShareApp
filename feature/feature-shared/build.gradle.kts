plugins {
    id("android-common-build")
}

dependencies {
    api(project(Module.coreUi))
    api(project(Module.coreDatasource))
    api(Libs.dataBindingLibrary)
    api(Libs.fragmentKTXLibrary)
    api(Libs.mockkLibrary)

}
