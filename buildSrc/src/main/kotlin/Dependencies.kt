object Libs {

    const val kotlinStandardLibrary =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_version}"

    const val appCompactLibrary = "androidx.appcompat:appcompat:${Versions.appcompat}"

    const val coreKTXLibrary = "androidx.core:core-ktx:${Versions.core_ktx}"

    const val vmSaveStateLibrary =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle_viewmodel_savedstate}"

    const val lifecycleRuntimeKTXLibrary =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_viewmodel_savedstate}"

    const val jsrLibrary = "javax.annotation:jsr250-api:${Versions.annotation_jsr250_api}"

    const val okHttpLibrary = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"

    const val fragmentKTXLibrary = "androidx.fragment:fragment-ktx:${Versions.fragment}"

    const val kotlinxCoroutinesCoreLibrary =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    const val kotlinxCoroutinesAndroidLibrary =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val constraintLayoutLibrary =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"

    const val recycleViewLibrary =
        "androidx.recyclerview:recyclerview:${Versions.recyclerview}"

    const val glideLibrary = "com.github.bumptech.glide:glide:${Versions.glide}"

    const val navigationLibrary =
        "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"

    const val navigationKTXLibrary =
        "androidx.navigation:navigation-ui:${Versions.nav_version}"

    const val navigationTestingLibrary =
        "androidx.navigation:navigation-testing:${Versions.nav_version}"

    const val navigationComposeLibrary =
        "androidx.navigation:navigation-compose:${Versions.nav_version}"

    const val timberLibrary = "com.jakewharton.timber:timber:${Versions.timber}"

    const val lottieLibrary = "com.airbnb.android:lottie:${Versions.lottie}"

    const val injectAssistedAnnotationDaggerLibrary =
        "com.squareup.inject:assisted-inject-annotations-dagger2:${Versions.assisted_inject_annotations_dagger}"

    const val hiltAndroidLibrary = "com.google.dagger:hilt-android:${Versions.hilt_version}"

    const val hiltKaptCompilerLibrary =
        "com.google.dagger:hilt-android-compiler:${Versions.hilt_version}"

    const val junitJupiterLibrary = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"

    const val junitJupiterEngineLibrary = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"

    const val androidxTestRunnerLibrary =
        "androidx.test:runner:${Versions.android_test_runner}"

    const val espressoCoreLibrary =
        "androidx.test.espresso:espresso-core:${Versions.espresso_core}"

    const val dataBindingLibrary =
        "androidx.databinding:databinding-runtime:${Versions.data_binding}"

    const val androidToolsBuildGradleLibrary = "com.android.tools.build:gradle:${Versions.gradle}"

    const val kotlinGradleGradlePluginLibrary =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"

    const val apolloGradlePluginLibrary =
        "com.apollographql.apollo3:apollo-gradle-plugin:${Versions.apollo}"

    const val hiltAndroidGradlePluginLibrary =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}"

    const val navSafeArgsGradlePluginLibrary =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav_version}"

    const val materialComponentsLibrary = "com.google.android.material:material:${Versions.material_components}"


}

object AppConfig {

    const val compileSdk = 33
    const val minSdk = 26
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "1.0"
    const val buildToolsVersion = "31.0.0"
    const val appId = "zi.aris.useronboarding"

    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"
    const val proguardConsumerRules = "consumer-rules.pro"
    const val proguardDefaultFile = "proguard-android-optimize.txt"

}
object Versions {

    const val gradle = "7.3.0"
    const val kotlin_version = "1.7.0"
    const val hilt_version = "2.43.2"
    const val nav_version = "2.5.2"
    const val data_binding = "7.3.0"
    const val appcompat = "1.4.1"
    const val core_ktx = "1.9.0"
    const val fragment = "1.5.2"
    const val lifecycle_viewmodel_savedstate = "2.5.1"
    const val annotation_jsr250_api = "1.0"
    const val okhttp = "4.9.3"
    const val apollo = "3.6.0"
    const val coroutines = "1.6.4"
    const val constraint_layout = "2.1.4"
    const val recyclerview = "1.2.1"
    const val glide = "4.12.0"
    const val timber = "4.7.1"
    const val lottie = "5.0.3"
    const val assisted_inject_annotations_dagger = "0.8.1"
    const val junit = "5.8.1"
    const val android_test_runner = "1.4.0"
    const val espresso_core = "3.4.0"
    const val material_components = "1.8.0-alpha01"

}

object Module {

    const val app = ":app"
    const val featureShared = ":feature:shared"
    const val featureSplashScreen = ":feature:splash-screen"
    const val featureOnboarding = ":feature:onboarding"
    const val featurePinLogin = ":feature:login"
    const val coreDesignSystem = ":core:design-system"
    const val coreDatasource = ":core:datasource"
    const val coreNavigation = ":core:navigation"
    const val coreKotlinUtils = ":core:kotlin-utils"
    const val testTesting = "test:testing"

}

