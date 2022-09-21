plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}
repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.3.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
}
