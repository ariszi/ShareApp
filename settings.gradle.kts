dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "User Onboarding"
include(":app")
include(":feature")
include(":core")
include(":core:datasource")
include(":core:navigation")
include(":core:design-system")
include(":core:ui")
include(":core:testing")
include(":feature:splash-screen")
include(":feature:onboarding")
include(":feature:login")
