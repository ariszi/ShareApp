package zi.aris.user

import zi.aris.ui.base_contracts.IntentAction
import zi.aris.ui.base_contracts.State

class UserProfileContract {

    sealed class UserProfileEvent : IntentAction {
        object UserLandedOnProfile : UserProfileEvent()
        object UserClickedOnSignOut : UserProfileEvent()
        object CleanNavigationEffect : UserProfileEvent()
        object CleanUserInfoEffect : UserProfileEvent()
    }

    data class UserProfileState(
        val navigation: UserNavigation = UserNavigation.Idle,
        val displayUserData: UserData = UserData.Idle
    ) : State

    sealed class UserNavigation {
        object Idle : UserNavigation()
        object NavigateToOnboarding : UserNavigation()
        object ExitApp : UserNavigation()
    }

    sealed class UserData {
        object Idle : UserData()
        data class UserError(val message: String) : UserData()
        data class UserInfo(val name: String, val lastName: String, val email: String) : UserData()
    }
}
