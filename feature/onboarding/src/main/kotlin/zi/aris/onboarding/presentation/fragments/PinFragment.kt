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
import zi.aris.onboarding.databinding.PinFragmentBinding
import zi.aris.onboarding.presentation.state.OnboardingStateContract
import zi.aris.onboarding.presentation.viewmodels.OnboardingViewModel
import zi.aris.ui.R.color


@AndroidEntryPoint
class PinFragment : Fragment(layout.pin_fragment) {

    private val viewModel: OnboardingViewModel by viewModels()

    private lateinit var next: TextView
    private lateinit var previous: TextView
    private lateinit var pin: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = PinFragmentBinding.bind(view)
        next = binding.next
        previous = binding.previous
        pin = binding.pinEt

        registerStateSubscriber()
        setupViewListeners()
        userLandedOnPinScreen()
    }

    private fun userLandedOnPinScreen() {
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.UserOnPinScreen)
    }

    private fun setupViewListeners() {
        next.setOnClickListener {
            viewModel.consumeEvent(
                OnboardingStateContract.OnboardingEvent.StepUserPinCompleted(
                    pin.text.toString(),
                )
            )
        }
        previous.setOnClickListener {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.GoBackToStepUserInfo)
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
            is OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserInfo -> {
                navigateToInfoScreen()
            }
            is OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserPinConfirmation -> {
                navigateToPinConfirmationScreen()
            }
            else -> {}
        }
    }

    private fun navigateToInfoScreen() {
        val action = PinFragmentDirections.actionPinFragmentToInfoFragment()
        this.findNavController().navigate(action)
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanNavigationEffect)
    }

    private fun navigateToPinConfirmationScreen() {
        val action = PinFragmentDirections.actionPinFragmentToPinConfirmationFragment()
        this.findNavController().navigate(action)
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanNavigationEffect)
    }

    private fun renderError(errorText: String) {
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
    }
}


