package zi.aris.onboarding.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import zi.aris.onboarding.presentation.state.OnboardingStateContract

@HiltViewModel
class OnboardingViewModel /*@Inject constructor(private val userDataUsecase: UserDataUsecase) */ : ViewModel() {

    private val loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val navigation: MutableStateFlow<OnboardingStateContract.UserOnboardingNavigation> =
        MutableStateFlow(OnboardingStateContract.UserOnboardingNavigation.Idle)

    //    private val navChooser =
//        flow {
//            emit(userDataUsecase.invoke())
//        }
//            .onStart { loading.update { true } }
//            .onEach { loading.update { false } }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5000L),
//                initialValue = OnboardingStateContract.UserNavOptions.Idle
//            )
    private fun getUserData() {


//        flow {
//            emit(userDataUsecase.invoke())
//        }
//            .onStart { loading.update { true } }
//            .onEach { loading.update { false } }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5000L),
//                initialValue = OnboardingStateContract.UserNavOptions.Idle
//            )
    }

    val state: StateFlow<OnboardingStateContract.OnboardingScreenState> =
        combine(loading, navigation) { loading, navigation ->
            OnboardingStateContract.OnboardingScreenState(
                loading,
                navigation
            )
        }.flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = OnboardingStateContract.OnboardingScreenState()
            )


    fun consumeEvent(event: OnboardingStateContract.OnboardingEvent) {
        when (event) {
            is OnboardingStateContract.OnboardingEvent.StepWelcomeCompleted -> {
                navigation.update { OnboardingStateContract.UserOnboardingNavigation.NavigateToStepTC }
            }
            OnboardingStateContract.OnboardingEvent.StepTCCompleted -> {
                //navigation.update { OnboardingStateContract.UserOnboardingNavigation.NavigateToStepTC }
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
                TODO()
            }
            is  OnboardingStateContract.OnboardingEvent.GoBackToStepUserCredential -> {
                TODO()
            }
            is  OnboardingStateContract.OnboardingEvent.GoBackToStepUserInfo -> {
                TODO()
            }
            is  OnboardingStateContract.OnboardingEvent.GoBackToStepUserPin -> {
                TODO()
            }
            is  OnboardingStateContract.OnboardingEvent.GoBackToStepWelcome -> {
                navigation.update { OnboardingStateContract.UserOnboardingNavigation.NavigateToStepWelcome }
            }
        }
    }

}
