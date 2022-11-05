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
include(":core:data-provider")
include(":feature:onboarding")
include(":feature:pin")
include(":feature:user")
include(":feature:feature-shared")
