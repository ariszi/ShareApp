plugins {
    id("android-common-build")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")

}

dependencies {
    api(project(Module.coreUi))
    api(project(Module.coreDatasource))
    api(Libs.dataBindingLibrary)
    api(Libs.fragmentKTXLibrary)
    api(Libs.hiltAndroidLibrary)
    api(Libs.hiltFragmentNavigationLibrary)

    kapt(Libs.hiltKaptCompilerLibrary)
}
