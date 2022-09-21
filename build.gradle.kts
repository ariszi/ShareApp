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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
