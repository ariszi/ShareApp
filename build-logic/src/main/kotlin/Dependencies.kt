

object AppConfig {

    const val versionCode = 1
    const val versionName = "1.0"
    const val appId = "zi.aris.useronboarding"
    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"
    const val proguardConsumerRules = "consumer-rules.pro"
    const val proguardDefaultFile = "proguard-android-optimize.txt"
    const val compose_compiler = "1.3.2"

}

object Module {

    const val app = ":app"
    const val featureShared = ":feature:feature-shared"
    const val featureOnboarding = ":feature:onboarding"
    const val featurePinLogin = ":feature:pin"
    const val featureMainScreen = ":feature:user-profile"
    const val coreDesignSystem = ":core:design-system"
    const val userRepository = ":feature:user-repository"
    const val coreUi = ":core:ui"
    const val testTesting = "core:testing"

}

