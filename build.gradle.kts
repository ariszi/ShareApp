buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Libs.androidToolsBuildGradleLibrary)
        classpath(Libs.kotlinGradleGradlePluginLibrary)
        classpath(Libs.hiltAndroidGradlePluginLibrary)
        classpath(Libs.navSafeArgsGradlePluginLibrary)
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
