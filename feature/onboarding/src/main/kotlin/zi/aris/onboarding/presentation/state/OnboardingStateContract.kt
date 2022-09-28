package zi.aris.onboarding.presentation.state

import zi.aris.ui.base_contracts.IntentAction
import zi.aris.ui.base_contracts.State

class OnboardingStateContract {

    sealed class OnboardingEvent : IntentAction {
        object StepWelcomeCompleted : OnboardingEvent()
        object StepTCCompleted : OnboardingEvent()
        object GoBackToStepWelcome : OnboardingEvent()
        object GoBackToStepTC : OnboardingEvent()
        data class StepUserCredentialCompleted(val email: String, val password: String) : OnboardingEvent()
        object GoBackToStepUserCredential : OnboardingEvent()
        data class StepUserInfoCompleted(val name: String, val lastName: String, val phone:String) : OnboardingEvent()
        object GoBackToStepUserInfo : OnboardingEvent()
        data class StepUserPinCompleted(val pin: String) : OnboardingEvent()
        object GoBackToStepUserPin : OnboardingEvent()
        data class StepUserPinConfirmationCompleted(val pin: String) : OnboardingEvent()
        object UserOnCredentialsScreen : OnboardingEvent()
        object UserOnInfoScreen : OnboardingEvent()
        object UserOnPinScreen : OnboardingEvent()
        object NextStepAvailable : OnboardingEvent()
        object NextStepUnavailable : OnboardingEvent()
        object CleanNavigationEffect : OnboardingEvent()
        object CleanUserInfoEffect : OnboardingEvent()
    }

    data class OnboardingScreenState(
        val isNextStepAvailable: Boolean = false,
        val navigation: UserOnboardingSteps = UserOnboardingSteps.Idle,
        val displayUserData: UserData = UserData.Idle,
    ) : State

    sealed class UserOnboardingSteps {
        object Idle : UserOnboardingSteps()
        object NavigateToStepTC : UserOnboardingSteps()
        object NavigateToStepWelcome : UserOnboardingSteps()
        object NavigateToStepUserCredentials : UserOnboardingSteps()
        object NavigateToStepUserInfo : UserOnboardingSteps()
        object NavigateToStepUserPin : UserOnboardingSteps()
        object NavigateToStepUserPinConfirmation : UserOnboardingSteps()
        object NavigateToUsersProfile : UserOnboardingSteps()
    }

    sealed class UserData {
        object Idle : UserData()
        object UserConfirmed : UserData()
        data class UserError(val message: String) : UserData()
        data class UserCredentials(val email: String) : UserData()
        data class UserInfo(val name: String, val lastName: String, val phone: String) : UserData()
    }
}
