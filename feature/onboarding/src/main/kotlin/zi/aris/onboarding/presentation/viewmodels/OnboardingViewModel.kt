package zi.aris.onboarding.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import zi.aris.onboarding.presentation.state.OnboardingStateContract
import zi.aris.onboarding.presentation.state.UserDataUsecase
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val userDataUsecase: UserDataUsecase) : ViewModel() {

    private val isNextStepAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val usersSteps: MutableStateFlow<OnboardingStateContract.UserOnboardingSteps> =
        MutableStateFlow(OnboardingStateContract.UserOnboardingSteps.Idle)

    private val userData: MutableStateFlow<OnboardingStateContract.UserData> =
        MutableStateFlow(OnboardingStateContract.UserData.Idle)

    private val userCredentialFlow =
        userData.filterNotNull().map { userDataUsecase.getUserCredentials() }.catch {
            OnboardingStateContract.UserData.UserError(UserDataUsecase.GENERIC_ERROR)
        }

    private val userInfoFlow =
        userData.filterNotNull().distinctUntilChanged().map { userDataUsecase.getUserInfo() }.catch {
            OnboardingStateContract.UserData.UserError(UserDataUsecase.GENERIC_ERROR)
        }

    val state: StateFlow<OnboardingStateContract.OnboardingScreenState> =
        combine(
            isNextStepAvailable,
            usersSteps,
            userData
        ) {  isNextStepAvailable, navigation, userData ->
            OnboardingStateContract.OnboardingScreenState(
                isNextStepAvailable,
                navigation,
                userData
            )
        }.flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = OnboardingStateContract.OnboardingScreenState()
            )

    private fun retrieveUsersCredential() {
        viewModelScope.launch { userData.update { userCredentialFlow.first() } }
    }

    private fun retrieveUsersInfo() {
        viewModelScope.launch { userData.update { userInfoFlow.first() } }
    }

    private fun saveUsersCredential(email: String, password: String) {
        viewModelScope.launch { userDataUsecase.saveUsersCredential(email, password) }
        usersSteps.update { OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserInfo }
    }

    private fun saveUsersInfo(name: String, lastName: String, phone: String) {
        viewModelScope.launch { userDataUsecase.saveUsersInfo(name, lastName, phone) }
        usersSteps.update { OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserPin }
    }

    private fun saveUsersPin(pin: String) {
        viewModelScope.launch { userDataUsecase.saveUsersPin(pin) }
        usersSteps.update { OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserPinConfirmation }
    }

    private fun confirmUsersPin(pin: String) {
        viewModelScope.launch { userData.update { userDataUsecase.confirmUsersPassword(pin) } }
    }


    fun consumeEvent(event: OnboardingStateContract.OnboardingEvent) {
        when (event) {
            is OnboardingStateContract.OnboardingEvent.StepWelcomeCompleted -> {
                usersSteps.update { OnboardingStateContract.UserOnboardingSteps.NavigateToStepTC }
            }
            is OnboardingStateContract.OnboardingEvent.StepTCCompleted -> {
                usersSteps.update { OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserCredentials }
            }
            is OnboardingStateContract.OnboardingEvent.StepUserCredentialCompleted -> {
                saveUsersCredential(event.email, event.password)
            }
            is OnboardingStateContract.OnboardingEvent.StepUserInfoCompleted -> {
                saveUsersInfo(event.name, event.lastName, event.phone)
            }
            is OnboardingStateContract.OnboardingEvent.StepUserPinCompleted -> {
                saveUsersPin(event.pin)
            }
            is OnboardingStateContract.OnboardingEvent.StepUserPinConfirmationCompleted -> {
                confirmUsersPin(event.pin)
            }
            is OnboardingStateContract.OnboardingEvent.GoBackToStepWelcome -> {
                usersSteps.update { OnboardingStateContract.UserOnboardingSteps.NavigateToStepWelcome }
            }
            is OnboardingStateContract.OnboardingEvent.GoBackToStepTC -> {
                usersSteps.update { OnboardingStateContract.UserOnboardingSteps.NavigateToStepTC }
            }
            is OnboardingStateContract.OnboardingEvent.GoBackToStepUserCredential -> {
                usersSteps.update { OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserCredentials }
            }
            is OnboardingStateContract.OnboardingEvent.GoBackToStepUserInfo -> {
                usersSteps.update { OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserInfo }
            }
            is OnboardingStateContract.OnboardingEvent.GoBackToStepUserPin -> {
                usersSteps.update { OnboardingStateContract.UserOnboardingSteps.NavigateToStepUserPin }
            }
            is OnboardingStateContract.OnboardingEvent.UserOnCredentialsScreen -> {
                retrieveUsersCredential()
            }
            is OnboardingStateContract.OnboardingEvent.UserOnInfoScreen -> {
                retrieveUsersInfo()
            }
            is OnboardingStateContract.OnboardingEvent.NextStepAvailable -> {
                isNextStepAvailable.update { true }
            }
            is OnboardingStateContract.OnboardingEvent.NextStepUnavailable -> {
                isNextStepAvailable.update { false }
            }
            is OnboardingStateContract.OnboardingEvent.CleanNavigationEffect -> {
                usersSteps.update { OnboardingStateContract.UserOnboardingSteps.Idle }
            }
            is OnboardingStateContract.OnboardingEvent.CleanUserInfoEffect -> {
                userData.update { OnboardingStateContract.UserData.Idle }
            }
            is OnboardingStateContract.OnboardingEvent.UserOnPinScreen -> {
                viewModelScope.launch { userDataUsecase.clearUsersPin() }
            }
        }
    }

}
