package zi.aris.onboarding.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zi.aris.onboarding.R.layout
import zi.aris.onboarding.presentation.state.OnboardingStateContract
import zi.aris.onboarding.presentation.viewmodels.OnboardingViewModel


@AndroidEntryPoint
class CredentialsFragment : Fragment(layout.credentials_fragment) {

    private val viewModel: OnboardingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerStateSubscriber()
    }

    private fun registerStateSubscriber() {
        lifecycleScope.launch { viewModel.state.collect { applyState(it) } }
    }

    private fun applyState(viewState: OnboardingStateContract.OnboardingScreenState) {
        navigate(viewState.navigation)
        insertUserInfo(viewState.displayUserInfo)
        renderLoading(viewState.loading)
    }

    private fun navigate(navigateToStep: OnboardingStateContract.UserOnboardingNavigation) {
        when (navigateToStep) {
            is OnboardingStateContract.UserOnboardingNavigation.NavigateToStepTC -> {
            }
            is OnboardingStateContract.UserOnboardingNavigation.NavigateToStepUserCredentials -> TODO()
            is OnboardingStateContract.UserOnboardingNavigation.NavigateToStepWelcome -> {
            }
            else -> {}
        }
    }

    private fun insertUserInfo(userInfo: OnboardingStateContract.UserInfo) {

    }

    private fun navigate(navigateBack: Boolean) {

    }

    private fun renderError(errorText: String) {
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
    }

    private fun renderLoading(visibility: Boolean) {
        // loading.visibilityExtension(visibility)
    }
}


