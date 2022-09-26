package zi.aris.onboarding.presentation.fragments

import android.os.Bundle
import android.text.Editable.Factory.getInstance
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import zi.aris.feature_shared.textObserver
import zi.aris.onboarding.R.layout
import zi.aris.onboarding.databinding.CredentialsFragmentBinding
import zi.aris.onboarding.presentation.state.OnboardingStateContract
import zi.aris.onboarding.presentation.viewmodels.OnboardingViewModel
import zi.aris.ui.R.color


@AndroidEntryPoint
class CredentialsFragment : Fragment(layout.credentials_fragment) {

    private val viewModel: OnboardingViewModel by viewModels()

    private lateinit var next: TextView
    private lateinit var previous: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = CredentialsFragmentBinding.bind(view)
        next = binding.next
        previous = binding.previous
        email = binding.emailEt
        password = binding.passwordEt

        registerStateSubscriber()
        setupViewListeners()
        userLandedOnCredentialsScreen()
    }

    private fun userLandedOnCredentialsScreen() {
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.UserOnCredentialsScreen)
    }

    private fun setupViewListeners() {
        next.setOnClickListener {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.StepTCCompleted)
        }
        previous.setOnClickListener {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.GoBackToStepTC)
        }
        val emailFieldObserver = email.textObserver()
        val passwordFieldObserver = password.textObserver()

        merge(emailFieldObserver, passwordFieldObserver)
            .debounce(300)
            .onStart { checkIfFieldsAreEmpty() }
            .onEach { checkIfFieldsAreEmpty() }
            .launchIn(lifecycleScope)
    }

    private fun checkIfFieldsAreEmpty() {
        if (email.text.toString().isNotEmpty() && password.text.toString().isNotEmpty()) {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.NextStepAvailable)
        } else {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.NextStepUnavailable)
        }
    }

    private fun registerStateSubscriber() {
        lifecycleScope.launch { viewModel.state.collect { applyState(it) } }
    }

    private fun applyState(viewState: OnboardingStateContract.OnboardingScreenState) {
        navigate(viewState.navigation)
        insertUserInfo(viewState.displayUserInfo)
        renderLoading(viewState.loading)
        renderStepAvailability(viewState.isNextStepAvailable)
    }

    private fun renderStepAvailability(nextStepAvailable: Boolean) {
        if (nextStepAvailable) {
            next.isClickable = true
            next.setTextColor(ContextCompat.getColor(this.requireContext(), color.purple_500))
        } else {
            next.isClickable = false
            next.setTextColor(ContextCompat.getColor(this.requireContext(), color.purple_200))
        }
    }

    private fun navigate(navigateToStep: OnboardingStateContract.UserOnboardingNavigation) {
        when (navigateToStep) {
            is OnboardingStateContract.UserOnboardingNavigation.NavigateToStepTC -> {
                navigateToTCScreen()
            }
            is OnboardingStateContract.UserOnboardingNavigation.NavigateToStepUserInfo -> {
                navigateToUserInfoScreen()
            }
            else -> {}
        }
    }

    private fun navigateToTCScreen() {
        val action = CredentialsFragmentDirections.actionCredentialsFragmentToTcFragment()
        this.findNavController().navigate(action)
    }

    private fun navigateToUserInfoScreen() {
//        val action = CredentialsFragmentDirections.()
//        this.findNavController().navigate(action)
    }

    private fun insertUserInfo(userCredentials: OnboardingStateContract.UserInfo) {
        if (userCredentials is OnboardingStateContract.UserInfo.UserCredentials) {
            email.text = getInstance().newEditable(userCredentials.email)
            password.text = getInstance().newEditable("")
        }
    }


    private fun renderError(errorText: String) {
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
    }

    private fun renderLoading(visibility: Boolean) {
        // loading.visibilityExtension(visibility)
    }
}


