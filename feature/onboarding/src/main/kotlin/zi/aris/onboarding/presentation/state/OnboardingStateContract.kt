package zi.aris.onboarding.presentation.state

import zi.aris.ui.base_contracts.IntentAction
import zi.aris.ui.base_contracts.State

class OnboardingStateContract {

    sealed class OnboardingEvent : IntentAction {
        object StepWelcomeCompleted : OnboardingEvent()
        object GoBackToStepWelcome : OnboardingEvent()
        object StepTCCompleted : OnboardingEvent()
        object GoBackToStepTC : OnboardingEvent()
        data class StepUserCredentialCompleted(val email: String, val password: String) : OnboardingEvent()
        object GoBackToStepUserCredential : OnboardingEvent()
        data class StepUserInfoCompleted(val name: String, val lastName: String) : OnboardingEvent()
        object GoBackToStepUserInfo : OnboardingEvent()
        data class StepUserPinCompleted(val pin: String) : OnboardingEvent()
        object GoBackToStepUserPin : OnboardingEvent()
        data class StepUserPinConfirmationCompleted(val pin: String) : OnboardingEvent()
    }

    data class OnboardingScreenState(
        val loading: Boolean = false,
        val navigation: UserOnboardingNavigation = UserOnboardingNavigation.Idle,
        val displayUserInfo: UserInfo = UserInfo.Idle,
        val genericError: String? = null
    ) : State

    sealed class UserOnboardingNavigation {
        object Idle : UserOnboardingNavigation()
        object NavigateToStepTC : UserOnboardingNavigation()
        object NavigateToStepWelcome : UserOnboardingNavigation()
        data class NavigateToStepUserCredentials(val email: String, val password: String) : UserOnboardingNavigation()
    }

    sealed class UserInfo {
        object Idle : UserInfo()
    }
}
