dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "User Onboarding"
include(":app")
include(":core:navigation")
include(":core:design-system")
include(":core:ui")
include(":core:testing")
include(":feature:splash-screen")
include(":feature:onboarding")
include(":feature:pin")
include(":feature:main-screen")
include(":core:data-provider")
