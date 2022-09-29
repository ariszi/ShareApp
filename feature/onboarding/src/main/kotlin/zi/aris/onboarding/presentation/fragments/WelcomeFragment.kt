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
import zi.aris.onboarding.databinding.WelcomeFragmentBinding
import zi.aris.onboarding.presentation.state.OnboardingStateContract
import zi.aris.onboarding.presentation.viewmodels.OnboardingViewModel

@AndroidEntryPoint
class WelcomeFragment : Fragment(R.layout.welcome_fragment) {
    private val viewModel: OnboardingViewModel by viewModels()
    lateinit var next: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = WelcomeFragmentBinding.bind(view)
        next = binding.next

        next.setOnClickListener {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.StepWelcomeCompleted)
        }
        registerStateSubscriber()
        registerViewObservers()

    }

    private fun registerViewObservers() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.ExitOnboarding)
        }
    }

    private fun registerStateSubscriber() {
        lifecycleScope.launch { viewModel.state.collect { applyState(it) } }
    }

    private fun applyState(viewState: OnboardingStateContract.OnboardingScreenState) {
        navigate(viewState.navigation)
    }

    private fun navigate(navChooser: OnboardingStateContract.UserOnboardingSteps) {
        when(navChooser){
            is OnboardingStateContract.UserOnboardingSteps.NavigateToStepTC ->{
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToTcFragment()
                this.findNavController().navigate(action)
                viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanNavigationEffect)
            }
            is OnboardingStateContract.UserOnboardingSteps.ExitApp ->{
                activity?.finish()
            }
            else -> {}
        }

    }
}


