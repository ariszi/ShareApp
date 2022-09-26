package zi.aris.presentation

import zi.aris.ui.base_contracts.IntentAction
import zi.aris.ui.base_contracts.State

class MainScreenContract {

    sealed class MainScreenEvent : IntentAction {
        object UserLaunchedApp : MainScreenEvent()
    }

    data class MainScreenState(
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
