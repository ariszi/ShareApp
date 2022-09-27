package zi.aris.onboarding.presentation.fragments

import android.os.Bundle
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import zi.aris.feature_shared.textObserver
import zi.aris.onboarding.R.layout
import zi.aris.onboarding.databinding.PinConfirmationFragmentBinding
import zi.aris.onboarding.presentation.state.OnboardingStateContract
import zi.aris.onboarding.presentation.viewmodels.OnboardingViewModel
import zi.aris.ui.R.color


@AndroidEntryPoint
class PinConfirmationFragment : Fragment(layout.pin_confirmation_fragment) {

    private val viewModel: OnboardingViewModel by viewModels()

    private lateinit var done: TextView
    private lateinit var previous: TextView
    private lateinit var pin: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = PinConfirmationFragmentBinding.bind(view)
        done = binding.done
        previous = binding.previous
        pin = binding.pinEt

        registerStateSubscriber()
        setupViewListeners()
    }

    private fun setupViewListeners() {
        done.setOnClickListener {
            viewModel.consumeEvent(
                OnboardingStateContract.OnboardingEvent.StepUserPinConfirmationCompleted(
                    pin.text.toString(),
                )
            )
        }
        previous.setOnClickListener {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.GoBackToStepUserPin)
        }
        pin.textObserver()
            .debounce(300)
            .onStart { checkIfFieldsAreEmpty() }
            .onEach { checkIfFieldsAreEmpty() }
            .launchIn(lifecycleScope)
    }

    private fun checkIfFieldsAreEmpty() {
        if (pin.text.toString().isNotEmpty()
        ) {
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
        renderLoading(viewState.loading)
        renderStepAvailability(viewState.isNextStepAvailable)
        renderUserInteraction(viewState.displayUserData)
    }

    private fun renderUserInteraction(displayUserData: OnboardingStateContract.UserData) {
        when (displayUserData) {
            is OnboardingStateContract.UserData.UserConfirmed -> Toast.makeText(activity, "Nice", Toast.LENGTH_LONG).show()
            is OnboardingStateContract.UserData.UserError -> Toast.makeText(
                activity,
                displayUserData.message,
                Toast.LENGTH_LONG
            ).show()
            else -> {}
        }
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanUserInfoEffect)
    }

    private fun renderStepAvailability(nextStepAvailable: Boolean) {
        if (nextStepAvailable) {
            done.isEnabled = true
            done.setTextColor(ContextCompat.getColor(this.requireContext(), color.purple_500))
        } else {
            done.isEnabled = false
            done.setTextColor(ContextCompat.getColor(this.requireContext(), color.purple_200))
        }
    }

    private fun navigate(navigateToStep: OnboardingStateContract.UserOnboardingSteps) {
        when (navigateToStep) {
            is OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserPin -> {
                navigateToPinScreen()
            }
            is OnboardingStateContract.UserOnboardingSteps.NavigateToUsersProfile -> {
                navigateToUsersProfile()
            }
            else -> {}
        }
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanNavigationEffect)
    }

    private fun navigateToPinScreen() {
        val action = PinConfirmationFragmentDirections.actionPinConfirmationFragmentToPinFragment()
        this.findNavController().navigate(action)
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanNavigationEffect)
    }

    private fun navigateToUsersProfile() {
//        val action = PinFragmentDirections.actionPinFragmentToInfoFragment()
//        this.findNavController().navigate(action)
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanNavigationEffect)
    }

    private fun renderError(errorText: String) {
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
    }

    private fun renderLoading(visibility: Boolean) {
        // loading.visibilityExtension(visibility)
    }
}


