import com.android.build.api.dsl.ApplicationExtension
import ext.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class ApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.findVersion("target_sdk").get().toString().toInt()
                packagingOptions {
                    resources {
                        excludes.addAll(
                            listOf(
                                "META-INF/**",
                                "**/attach_hotspot_windows.dll"
                            )
                        )
                    }
                }
            }
        }
    }




}
