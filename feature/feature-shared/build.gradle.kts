plugins{
    id("android-common-build")
}



dependencies{
    api(project(Module.coreUi))
    api(Libs.fragmentKTXLibrary)
}
