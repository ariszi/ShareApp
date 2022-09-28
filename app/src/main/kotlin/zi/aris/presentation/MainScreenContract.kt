package zi.aris.presentation

import zi.aris.ui.base_contracts.IntentAction
import zi.aris.ui.base_contracts.State

class MainScreenContract {

    sealed class MainScreenEvent : IntentAction {
        object UserLaunchedApp : MainScreenEvent()
    }

    data class MainScreenState(
        val navChooser: UserNavOptions = UserNavOptions.Idle,
    ) : State

    sealed class UserNavOptions {
        object Idle : UserNavOptions()
        object NavigateToOnboarding : UserNavOptions()
        object NavigateToPinSignIn : UserNavOptions()

    }
}
