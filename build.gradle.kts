buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.android.gradlePlugin)
        classpath(libs.hilt.gradlePlugin)
        classpath(libs.safe.args.gradlePlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
