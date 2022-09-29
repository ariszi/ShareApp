package zi.aris.onboarding.presentation.fragments

import android.os.Bundle
import android.text.Editable.Factory.getInstance
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import zi.aris.feature_shared.textObserver
import zi.aris.onboarding.R
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
            viewModel.consumeEvent(
                OnboardingStateContract.OnboardingEvent.StepUserCredentialCompleted(
                    email.text.toString(),
                    password.text.toString()
                )
            )
        }
        previous.setOnClickListener {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.GoBackToStepTC)
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
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
        renderUserCredentials(viewState.displayUserData)
        renderStepAvailability(viewState.isNextStepAvailable)
    }

    private fun renderStepAvailability(nextStepAvailable: Boolean) {
        if (nextStepAvailable) {
            next.isEnabled = true
            next.setTextColor(ContextCompat.getColor(this.requireContext(), color.purple_500))
        } else {
            next.isEnabled = false
            next.setTextColor(ContextCompat.getColor(this.requireContext(), color.purple_200))
        }
    }

    private fun navigate(navigateToStep: OnboardingStateContract.UserOnboardingSteps) {
        when (navigateToStep) {
            is OnboardingStateContract.UserOnboardingSteps.NavigateToStepTC -> {
                navigateToTCScreen()
            }
            is OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserInfo -> {
                navigateToUserInfoScreen()
            }
            else -> {}
        }
    }

    private fun navigateToTCScreen() {
        val action = CredentialsFragmentDirections.actionCredentialsFragmentToTcFragment()
        this.findNavController().navigate(action)
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanNavigationEffect)
    }

    private fun navigateToUserInfoScreen() {
        val action = CredentialsFragmentDirections.actionCredentialsFragmentToInfoFragment()
        this.findNavController().navigate(action)
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanNavigationEffect)
    }

    private fun renderUserCredentials(userCredentials: OnboardingStateContract.UserData) {
        when (userCredentials) {
            is OnboardingStateContract.UserData.UserError -> {
                /*todo display error*/
            }
            is OnboardingStateContract.UserData.UserCredentials -> {
                email.text = getInstance().newEditable(userCredentials.email)
                password.text = getInstance().newEditable("")
                Toast.makeText(activity, getString(R.string.password_disclaimer), Toast.LENGTH_SHORT).show()
                viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanUserInfoEffect)
            }
            else -> {}
        }
    }

}


