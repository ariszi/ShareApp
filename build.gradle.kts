buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Plugin.androidToolsBuildGradleLibrary)
        classpath(Plugin.kotlinGradleGradlePluginLibrary)
        classpath(Plugin.hiltAndroidGradlePluginLibrary)
        classpath(Plugin.navSafeArgsGradlePluginLibrary)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
