package zi.aris.onboarding.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
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

    lateinit var next: TextView
    lateinit var previous: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = TcFragmentBinding.bind(view)
        next = binding.next
        previous = binding.previous

        next.setOnClickListener {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.StepTCCompleted)
        }
        previous.setOnClickListener {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.GoBackToStepWelcome)
        }

        registerStateSubscriber()
    }

    private fun registerStateSubscriber() {
        lifecycleScope.launch { viewModel.state.collect { applyState(it) } }
    }

    private fun applyState(viewState: OnboardingStateContract.OnboardingScreenState) {
        navigate(viewState.navigation)
//        insertUserInfo(viewState.displayUserInfo)
//        renderLoading(viewState.loading)
    }

    private fun navigate(navChooser: OnboardingStateContract.UserOnboardingNavigation) {
        when (navChooser) {
            is OnboardingStateContract.UserOnboardingNavigation.NavigateToStepWelcome ->
                navigateToWelcomeScreen()
            is OnboardingStateContract.UserOnboardingNavigation.NavigateToStepUserCredentials ->
                navigateToCredentialsScreen()
            else -> {}
        }

    }

    private fun navigateToWelcomeScreen() {
        val action = TCFragmentDirections.actionTcFragmentToWelcomeFragment()
        this.findNavController().navigate(action)
    }

    private fun navigateToCredentialsScreen() {
        val action = TCFragmentDirections.actionTcFragmentToCredentialsFragment()
        this.findNavController().navigate(action)
    }


}


