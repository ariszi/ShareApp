package zi.aris.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val userUsecase: UserProfileUsecase) : ViewModel() {

    private val navigation: MutableStateFlow<UserProfileContract.UserNavigation> =
        MutableStateFlow(UserProfileContract.UserNavigation.Idle)

    private val userData: MutableStateFlow<UserProfileContract.UserData> =
        MutableStateFlow(UserProfileContract.UserData.Idle)

    private val userCredentialFlow =
        userData.filterNotNull().map { userUsecase.getUserInfo() }.catch {
            UserProfileContract.UserData.UserError(UserProfileUsecase.GENERIC_ERROR)
        }


    val state: StateFlow<UserProfileContract.UserProfileState> =
        combine(navigation, userData) { navigation, userData ->
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
