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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
