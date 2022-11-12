dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
rootProject.name = "Share"
include(":app")
include(":core:design-system")
include(":core:ui")
include(":core:user-data")
include(":feature:onboarding")
include(":feature:pin")
include(":feature:user-profile")
include(":feature:feature-shared")
