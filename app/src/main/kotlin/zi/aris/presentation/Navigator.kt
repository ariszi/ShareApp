package zi.aris.presentation

import android.content.Context
import androidx.navigation.fragment.findNavController
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import zi.aris.login.LoginRouter
import zi.aris.onboarding.presentation.OnboardingRouter
import zi.aris.user.UserProfileRouter
import zi.aris.useronboarding.R
import javax.inject.Inject

@InstallIn(ActivityComponent::class)
@Module
class Navigator @Inject constructor(
    private val context: Context
) : LoginRouter, UserProfileRouter, OnboardingRouter {

    private val myActivity = context as MainActivity
    private val hostFragment = myActivity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

    override fun navigateFromLoginToUserProfile() {
        hostFragment?.findNavController()?.setGraph(zi.aris.user.R.navigation.profile_nav_graph)
    }

    override fun navigateFromProfileToOnBoarding() {
        hostFragment?.findNavController()?.setGraph(zi.aris.onboarding.R.navigation.onboarding_nav_graph)
    }

    override fun navigateFromOnboardingToUserProfile() {
        hostFragment?.findNavController()?.setGraph(zi.aris.user.R.navigation.profile_nav_graph)
    }
}
