package zi.aris.onboarding.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import zi.aris.onboarding.presentation.state.OnboardingStateContract
import zi.aris.onboarding.presentation.state.UserDataUsecase
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val userDataUsecase: UserDataUsecase) : ViewModel() {

    private val loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val isNextStepAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val navigation: MutableStateFlow<OnboardingStateContract.UserOnboardingNavigation> =
        MutableStateFlow(OnboardingStateContract.UserOnboardingNavigation.Idle)

    private val userInfo: MutableStateFlow<OnboardingStateContract.UserInfo> =
        MutableStateFlow(OnboardingStateContract.UserInfo.Idle)

    private val userCredential =
        flow {
            emit(userDataUsecase.getUserCredentials())
        }
            .onStart { loading.update { true } }
            .onEach { loading.update { false } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = OnboardingStateContract.UserInfo.Idle
            )

    val state: StateFlow<OnboardingStateContract.OnboardingScreenState> =
        combine(loading, isNextStepAvailable, navigation, userInfo) { loading, isNextStepAvailable, navigation, userInfo ->
            OnboardingStateContract.OnboardingScreenState(
                loading,
                isNextStepAvailable,
                navigation,
                userInfo
            )
        }.flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = OnboardingStateContract.OnboardingScreenState()
            )

    private fun retrieveUsersCredential() {
        userInfo.update { userCredential.value }
    }


    fun consumeEvent(event: OnboardingStateContract.OnboardingEvent) {
        when (event) {
            is OnboardingStateContract.OnboardingEvent.StepWelcomeCompleted -> {
                navigation.update { OnboardingStateContract.UserOnboardingNavigation.NavigateToStepTC }
            }
            OnboardingStateContract.OnboardingEvent.StepTCCompleted -> {
                navigation.update { OnboardingStateContract.UserOnboardingNavigation.NavigateToStepUserCredentials }
            }
            is OnboardingStateContract.OnboardingEvent.StepUserCredentialCompleted -> {
                TODO()
            }
            is OnboardingStateContract.OnboardingEvent.StepUserInfoCompleted -> {
                TODO()
            }
            is OnboardingStateContract.OnboardingEvent.StepUserPinCompleted -> {
                TODO()
            }
            is OnboardingStateContract.OnboardingEvent.StepUserPinConfirmationCompleted -> {
                TODO()
            }
            is OnboardingStateContract.OnboardingEvent.GoBackToStepTC -> {
                navigation.update { OnboardingStateContract.UserOnboardingNavigation.NavigateToStepTC }
            }
            is OnboardingStateContract.OnboardingEvent.GoBackToStepUserCredential -> {
                TODO()
            }
            is OnboardingStateContract.OnboardingEvent.GoBackToStepUserInfo -> {
                TODO()
            }
            is OnboardingStateContract.OnboardingEvent.GoBackToStepUserPin -> {
                TODO()
            }
            is OnboardingStateContract.OnboardingEvent.GoBackToStepWelcome -> {
                navigation.update { OnboardingStateContract.UserOnboardingNavigation.NavigateToStepWelcome }
            }
            is OnboardingStateContract.OnboardingEvent.UserOnCredentialsScreen -> {
                retrieveUsersCredential()
            }
            is OnboardingStateContract.OnboardingEvent.NextStepAvailable -> {
                isNextStepAvailable.update { true }
            }
            is OnboardingStateContract.OnboardingEvent.NextStepUnavailable -> {
                isNextStepAvailable.update { false }
            }
        }
    }

}
