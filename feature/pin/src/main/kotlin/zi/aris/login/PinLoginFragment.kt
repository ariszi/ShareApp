package zi.aris.login

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import zi.aris.feature_shared.textObserver
import zi.aris.pin.R
import zi.aris.pin.databinding.PinLoginFragmentBinding
import javax.inject.Inject

@AndroidEntryPoint
class PinLoginFragment : Fragment(R.layout.pin_login_fragment) {

    @Inject
    lateinit var router: LoginRouter

    private val viewModel: PinLoginViewModel by viewModels()

    private lateinit var pin: EditText
    private lateinit var login: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = PinLoginFragmentBinding.bind(view)
        pin = binding.pinEt
        login = binding.signinBtn

        registerStateSubscriber()
        setupViewListeners()
    }

    private fun setupViewListeners() {
        login.setOnClickListener {
            viewModel.consumeEvent(
                LoginContract.UserLoginEvent.UserEnteredPin(
                    pin.text.toString()
                )
            )
        }
        pin.textObserver().debounce(300)
            .onStart { checkIfFieldsAreEmpty() }
            .onEach { checkIfFieldsAreEmpty() }
            .launchIn(lifecycleScope)

    }

    private fun checkIfFieldsAreEmpty() {
        if (pin.text.toString().isNotEmpty()) {
            viewModel.consumeEvent(LoginContract.UserLoginEvent.LoginAvailable)
        } else {
            viewModel.consumeEvent(LoginContract.UserLoginEvent.LoginUnavailable)
        }
    }

    private fun registerStateSubscriber() {
        lifecycleScope.launch { viewModel.state.collect { applyState(it) } }
    }

    private fun applyState(viewState: LoginContract.UserLoginState) {
        renderUserPinAttempt(viewState.displayUserData)
        renderStepAvailability(viewState.isNextStepAvailable)
    }

    private fun renderUserPinAttempt(userValidation: LoginContract.UserData) {
        when (userValidation) {
            is LoginContract.UserData.UserError -> {
                Toast.makeText(activity, userValidation.message, Toast.LENGTH_LONG).show()
                viewModel.consumeEvent(LoginContract.UserLoginEvent.CleanUserInfoEffect)
            }
            is LoginContract.UserData.UserIsValid -> {
                navigateToUserProfile()
            }
            else -> {}
        }
    }

    private fun navigateToUserProfile() {
        router.navigateFromLoginToUserProfile()
        viewModel.consumeEvent(LoginContract.UserLoginEvent.CleanUserInfoEffect)
    }

    private fun renderStepAvailability(nextStepAvailable: Boolean) {
        if (nextStepAvailable) {
            login.isEnabled = true
            login.setTextColor(ContextCompat.getColor(this.requireContext(), zi.aris.ui.R.color.purple_500))
        } else {
            login.isEnabled = false
            login.setTextColor(ContextCompat.getColor(this.requireContext(), zi.aris.ui.R.color.purple_200))
        }
    }

}
