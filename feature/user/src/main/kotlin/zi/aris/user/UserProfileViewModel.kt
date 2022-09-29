package zi.aris.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userUsecase: UserProfileUsecase
) : ViewModel() {

    private companion object {

        const val USER_PROFILE_ID = "user_profile_id"
    }

    private val userNavSavedState = savedStateHandle
        .getStateFlow<UserProfileContract.UserNavigation>(
            USER_PROFILE_ID,
            UserProfileContract.UserNavigation.Idle
        ).filterNotNull()


    private val navigation: MutableStateFlow<UserProfileContract.UserNavigation> =
        MutableStateFlow(UserProfileContract.UserNavigation.Idle)

    private val navFlow = merge(userNavSavedState, navigation)

    private val userData: MutableStateFlow<UserProfileContract.UserData> =
        MutableStateFlow(UserProfileContract.UserData.Idle)

    private val userCredentialFlow =
        userData.filterNotNull().map { userUsecase.getUserInfo() }.catch {
            UserProfileContract.UserData.UserError(UserProfileUsecase.GENERIC_ERROR)
        }


    val state: StateFlow<UserProfileContract.UserProfileState> =
        combine(navFlow, userData) { navigation, userData ->
            UserProfileContract.UserProfileState(
                navigation,
                userData
            )
        }.flowOn(Dispatchers.Default)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = UserProfileContract.UserProfileState()
            )

    private fun signOutUser() {
        viewModelScope.launch { userUsecase.signOut() }
        navigation.update { UserProfileContract.UserNavigation.NavigateToOnboarding }
    }

    fun consumeEvent(event: UserProfileContract.UserProfileEvent) {
        when (event) {
            is UserProfileContract.UserProfileEvent.UserLandedOnProfile ->
                retrieveUsersInfo()
            is UserProfileContract.UserProfileEvent.UserClickedOnSignOut -> {
                signOutUser()
            }
            is UserProfileContract.UserProfileEvent.CleanNavigationEffect -> {
                navigation.update { UserProfileContract.UserNavigation.Idle }
            }
            is UserProfileContract.UserProfileEvent.CleanUserInfoEffect -> {
                userData.update { UserProfileContract.UserData.Idle }
            }
        }
    }

    private fun retrieveUsersInfo() {
        viewModelScope.launch { userData.update { userCredentialFlow.first() } }
    }


}
