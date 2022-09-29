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
import zi.aris.onboarding.R.layout
import zi.aris.onboarding.databinding.InfoFragmentBinding
import zi.aris.onboarding.presentation.state.OnboardingStateContract
import zi.aris.onboarding.presentation.viewmodels.OnboardingViewModel
import zi.aris.ui.R.color


@AndroidEntryPoint
class InfoFragment : Fragment(layout.info_fragment) {

    private val viewModel: OnboardingViewModel by viewModels()

    private lateinit var next: TextView
    private lateinit var previous: TextView
    private lateinit var name: EditText
    private lateinit var lastName: EditText
    private lateinit var phone: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = InfoFragmentBinding.bind(view)
        next = binding.next
        previous = binding.previous
        name = binding.nameEt
        lastName = binding.lastNameEt
        phone = binding.phoneEt

        registerStateSubscriber()
        setupViewListeners()
        userLandedOnInfoScreen()
    }

    private fun userLandedOnInfoScreen() {
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.UserOnInfoScreen)
    }

    private fun setupViewListeners() {
        next.setOnClickListener {
            viewModel.consumeEvent(
                OnboardingStateContract.OnboardingEvent.StepUserInfoCompleted(
                    name.text.toString(),
                    lastName.text.toString(),
                    phone.text.toString()
                )
            )
        }
        previous.setOnClickListener {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.GoBackToStepUserCredential)
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.GoBackToStepUserCredential)
        }
        val nameFieldObserver = name.textObserver()
        val lastNameFieldObserver = lastName.textObserver()
        val phoneFieldObserver = phone.textObserver()

        merge(nameFieldObserver, lastNameFieldObserver, phoneFieldObserver)
            .debounce(300)
            .onStart { checkIfFieldsAreEmpty() }
            .onEach { checkIfFieldsAreEmpty() }
            .launchIn(lifecycleScope)
    }

    private fun checkIfFieldsAreEmpty() {
        if (name.text.toString().isNotEmpty() &&
            lastName.text.toString().isNotEmpty() &&
            phone.text.toString().isNotEmpty()
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
        insertUserInfo(viewState.displayUserData)
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
            is OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserCredentials -> {
                navigateToCredentialsScreen()
            }
            is OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserPin -> {
                navigateToPinScreen()
            }
            else -> {}
        }
    }

    private fun navigateToCredentialsScreen() {
        val action = InfoFragmentDirections.actionInfoFragmentToCredentialsFragment()
        this.findNavController().navigate(action)
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanNavigationEffect)
    }

    private fun navigateToPinScreen() {
        val action = InfoFragmentDirections.actionInfoFragmentToPinFragment()
        this.findNavController().navigate(action)
        viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanNavigationEffect)
    }

    private fun insertUserInfo(userInfo: OnboardingStateContract.UserData) {
        if (userInfo is OnboardingStateContract.UserData.UserInfo) {
            name.text = getInstance().newEditable(userInfo.name)
            lastName.text = getInstance().newEditable(userInfo.lastName)
            phone.text = getInstance().newEditable(userInfo.phone)
            viewModel.consumeEvent(OnboardingStateContract.OnboardingEvent.CleanUserInfoEffect)
        }
    }


    private fun renderError(errorText: String) {
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
    }

}


