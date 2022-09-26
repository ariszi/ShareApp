package zi.aris.onboarding.presentation.state

import zi.aris.data_provider.domain.UserRepositoryContract
import javax.inject.Inject

class UserDataUsecase @Inject constructor(private val userData: UserRepositoryContract) {

//    suspend fun getSmt(): OnboardingStateContract.UserInfo {
//        return when (userData.isUserRegistered()) {
//            true -> OnboardingStateContract.UserNavOptions.NavigateToPinSignIn
//            false -> OnboardingStateContract.UserNavOptions.NavigateToOnboarding
//        }
//    }
}
