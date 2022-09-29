package zi.aris.onboarding.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zi.aris.onboarding.R
import zi.aris.onboarding.databinding.TcFragmentBinding
import zi.aris.onboarding.presentation.state.OnboardingStateContract
import zi.aris.onboarding.presentation.viewmodels.OnboardingViewModel

@AndroidEntryPoint
class TCFragment : Fragment(R.layout.tc_fragment) {

    private val viewModel: OnboardingViewModel by viewModels()

    private lateinit var next: TextView
    private lateinit var previous: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = TcFragmentBinding.bind(view)
        next = binding.next
        previous = binding.previous

        registerStateSubscriber()
        setupViewListeners()
    }

    private fun setupViewListeners() {
        next.setOnClickListener {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.StepTCCompleted)
        }
        previous.setOnClickListener {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.GoBackToStepWelcome)
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.GoBackToStepWelcome)
        }
    }

    private fun registerStateSubscriber() {
        lifecycleScope.launch { viewModel.state.collect { applyState(it) } }
    }

    private fun applyState(viewState: OnboardingStateContract.OnboardingScreenState) {
        navigate(viewState.navigation)
    }

    private fun navigate(navChooser: OnboardingStateContract.UserOnboardingSteps) {
        when (navChooser) {
            is OnboardingStateContract.UserOnboardingSteps.NavigateToStepWelcome ->
                navigateToWelcomeScreen()
            is OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserCredentials ->
                navigateToCredentialsScreen()
            else -> {}
        }

    }

    private fun navigateToWelcomeScreen() {
        val action = TCFragmentDirections.actionTcFragmentToWelcomeFragment()
        this.findNavController().navigate(action)
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanNavigationEffect)
    }

    private fun navigateToCredentialsScreen() {
        val action = TCFragmentDirections.actionTcFragmentToCredentialsFragment()
        this.findNavController().navigate(action)
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanNavigationEffect)
    }


}


