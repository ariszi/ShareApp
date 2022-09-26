package zi.aris.presentation

import zi.aris.data_provider.domain.UserRepositoryContract
import javax.inject.Inject

class UserNavigationChooserUsecase @Inject constructor(private val userData: UserRepositoryContract) {

    suspend fun invoke(): MainScreenContract.UserNavOptions {
        return when (userData.isUserRegistered()) {
            true -> MainScreenContract.UserNavOptions.NavigateToPinSignIn
            false -> MainScreenContract.UserNavOptions.NavigateToOnboarding
        }
    }
}
