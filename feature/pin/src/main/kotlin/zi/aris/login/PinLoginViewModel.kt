package zi.aris.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinLoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userUsecase: UserValidationUsecase
) : ViewModel() {

    private companion object {

        const val LOGIN_ID = "login_id"
    }

    private val userLoginSavedState = savedStateHandle
        .getStateFlow<LoginContract.UserData>(
            LOGIN_ID,
            LoginContract.UserData.Idle
        ).filterNotNull()

    private val isNextStepAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val userLogin: MutableStateFlow<LoginContract.UserData> =
        MutableStateFlow(LoginContract.UserData.Idle)

    private val userLoginFlow = merge(userLoginSavedState, userLogin)

    val state: StateFlow<LoginContract.UserLoginState> =
        combine(userLoginFlow, isNextStepAvailable) { userLogin, isNextStepAvailable ->
            LoginContract.UserLoginState(
                isNextStepAvailable = isNextStepAvailable,
                displayUserData = userLogin
            )
        }.flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = LoginContract.UserLoginState()
            )


    private fun loginUser(pin: String) {
        viewModelScope.launch { userLogin.update { userUsecase.validateUser(pin) } }
    }

    fun consumeEvent(event: LoginContract.UserLoginEvent) {
        when (event) {
            is LoginContract.UserLoginEvent.UserEnteredPin -> loginUser(event.pin)
            is LoginContract.UserLoginEvent.LoginAvailable -> isNextStepAvailable.update { true }
            is LoginContract.UserLoginEvent.LoginUnavailable -> isNextStepAvailable.update { false }
            is LoginContract.UserLoginEvent.CleanUserInfoEffect -> userLogin.update { LoginContract.UserData.Idle }
        }
    }

}
