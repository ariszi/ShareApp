dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "User Onboarding"
include(":app")
include(":core:design-system")
include(":core:ui")
include(":core:testing")
include(":core:data-provider")
include(":feature:onboarding")
include(":feature:pin")
include(":feature:user")
include(":feature:feature-shared")
