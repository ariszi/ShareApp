package zi.aris.login

import zi.aris.ui.base_contracts.IntentAction
import zi.aris.ui.base_contracts.State
import java.io.Serializable

class LoginContract {
    sealed class UserLoginEvent : IntentAction {
        data class UserEnteredPin(val pin: String) : UserLoginEvent()
        object LoginAvailable : UserLoginEvent()
        object LoginUnavailable : UserLoginEvent()
        object CleanUserInfoEffect : UserLoginEvent()
    }

    data class UserLoginState(
        val isNextStepAvailable: Boolean = false,
        val displayUserData: UserData = UserData.Idle
    ) : State

    sealed class UserData : Serializable {
        object Idle : UserData()
        data class UserError(val message: String) : UserData()
        object UserIsValid : UserData()
    }
}
