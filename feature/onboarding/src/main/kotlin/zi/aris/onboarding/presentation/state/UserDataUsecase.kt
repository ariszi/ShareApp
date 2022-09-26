package zi.aris.onboarding.presentation.state

import kotlinx.coroutines.flow.first
import zi.aris.data_provider.domain.Result
import zi.aris.data_provider.domain.User
import zi.aris.data_provider.domain.UserRepositoryContract
import javax.inject.Inject

class UserDataUsecase @Inject constructor(private val userData: UserRepositoryContract) {

    suspend fun getUserCredentials(): OnboardingStateContract.UserInfo {
        return when (val result = userData.usersCredentials().first()) {
            is Result.Success -> result.data.toUserCredentials()
            is Result.Error -> OnboardingStateContract.UserInfo.Idle
        }
    }

    private fun User.toUserCredentials() = OnboardingStateContract.UserInfo.UserCredentials(email, password)
}
