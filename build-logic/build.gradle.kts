plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

gradlePlugin {
    plugins {
        register("applicationCP") {
            id = "shareapp.android.application"
            implementationClass = "ApplicationConventionPlugin"
        }
        register("androidLibraryCP") {
            id = "shareapp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("appDICP") {
            id = "shareapp.di"
            implementationClass = "DIConventionPlugin"
        }
    }
}
