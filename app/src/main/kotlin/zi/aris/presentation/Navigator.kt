package zi.aris.presentation

import android.content.Context
import androidx.navigation.fragment.findNavController
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import zi.aris.login.LoginRouter
import zi.aris.onboarding.presentation.OnboardingRouter
import zi.aris.user.UserProfileRouter
import zi.aris.useronboarding.MainNavGraphDirections
import zi.aris.useronboarding.R
import javax.inject.Inject

@InstallIn(ActivityComponent::class)
@Module
class Navigator @Inject constructor(
    private val context: Context
) : LoginRouter, UserProfileRouter, OnboardingRouter, MainRouter {

    private val myActivity = context as MainActivity
    private val hostFragment = myActivity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

    override fun navigateFromLoginToUserProfile() {
        hostFragment?.findNavController()?.navigate(MainNavGraphDirections.actionToProfileNavGraph())
    }

    override fun navigateFromProfileToOnBoarding() {
        hostFragment?.findNavController()?.navigate(MainNavGraphDirections.actionToOnboardingNavGraph())
    }

    override fun navigateFromOnboardingToUserProfile() {
        hostFragment?.findNavController()?.navigate(MainNavGraphDirections.actionToProfileNavGraph())
    }

    override fun navigateToPinLogin() {
        hostFragment?.findNavController()?.navigate(MainNavGraphDirections.actionToPinNavGraph())
    }

    //The code is the same but i would feel safer with duplicate and different fun naming if
    // in the future one of the functions needs to be updated
    override fun navigateToOnBoarding() {
        hostFragment?.findNavController()?.navigate(MainNavGraphDirections.actionToOnboardingNavGraph())
    }
}
