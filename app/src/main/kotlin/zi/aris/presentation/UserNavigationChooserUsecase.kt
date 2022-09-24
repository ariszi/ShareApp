package zi.aris.presentation

import zi.aris.data_provider.domain.UserRepositoryContract
import javax.inject.Inject

class UserNavigationChooserUsecase @Inject constructor(private val userData: UserRepositoryContract) {

    suspend fun invoke(): SplashScreenContract.UserNavOptions {
        return when (userData.isUserRegistered()) {
            true -> SplashScreenContract.UserNavOptions.NavigateToPinSignIn
            false -> SplashScreenContract.UserNavOptions.NavigateToOnboarding
        }
    }
}
