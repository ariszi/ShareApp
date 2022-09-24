package zi.aris.presentation

import zi.aris.ui.base_contracts.IntentAction
import zi.aris.ui.base_contracts.State

class SplashScreenContract {

    sealed class SplashScreenEvent : IntentAction {
        object UserLaunchedApp : SplashScreenEvent()
    }

    data class SplashScreenState(
        val loading: Boolean = false,
        val navChooser: UserNavOptions = UserNavOptions.Idle,
        val genericError: String? = null
    ) : State

    sealed class UserNavOptions {
        object Idle : UserNavOptions()
        object NavigateToOnboarding : UserNavOptions()
        object NavigateToPinSignIn : UserNavOptions()

    }
}
