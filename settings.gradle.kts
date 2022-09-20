dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "User Onboarding"
include(":app")
include(":feature:shared")
include(":feature:splash-screen")
include(":feature:onboarding")
include(":feature:login")
include(":core:design-system")
include(":core:datasource")
include(":core:navigation")
include(":core:kotlin-utils")
include("test:common")
