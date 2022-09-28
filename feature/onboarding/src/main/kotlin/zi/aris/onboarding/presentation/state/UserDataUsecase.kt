package zi.aris.onboarding.presentation.state

import kotlinx.coroutines.flow.first
import zi.aris.data_provider.domain.ErrorWithMessage
import zi.aris.data_provider.domain.Result
import zi.aris.data_provider.domain.User
import zi.aris.data_provider.domain.UserRepositoryContract
import javax.inject.Inject

class UserDataUsecase @Inject constructor(private val userData: UserRepositoryContract) {

    companion object {

        const val GENERIC_ERROR = "Wow. Sorry that never happened before"

    }

    suspend fun getUserCredentials(): OnboardingStateContract.UserData {
        return when (val result = userData.usersCredentials().first()) {
            is Result.Success -> result.data.toUserCredentials()
            else -> OnboardingStateContract.UserData.Idle
        }
    }

    suspend fun getUserInfo(): OnboardingStateContract.UserData {
        return when (val result = userData.usersInfo().first()) {
            is Result.Success -> result.data.toUserInfo()
            is Result.Error -> result.toDataError()
            else -> {
                OnboardingStateContract.UserData.UserError(GENERIC_ERROR)
            }
        }
    }

    suspend fun saveUsersCredential(email: String, password: String) {
        return userData.saveUsersEmailAndPassword(email, password)
    }

    suspend fun saveUsersInfo(name: String, lastName: String, phone: String) {
        return userData.saveUsersInfo(name, lastName, phone)
    }

    suspend fun saveUsersPin(pin: String) {
        return userData.saveUsersPin(pin)
    }

    suspend fun clearUsersPin() {
        return userData.cleanUsersPin()
    }


    private fun User.toUserCredentials(): OnboardingStateContract.UserData {
        return if (email.isEmpty()) {
            OnboardingStateContract.UserData.Idle
        } else {
            OnboardingStateContract.UserData.UserCredentials(email)
        }
    }

    private fun User.toUserInfo() = OnboardingStateContract.UserData.UserInfo(name, lastName, telephone)

    private fun Result.Error.toDataError(): OnboardingStateContract.UserData.UserError {
        return when (this) {
            is ErrorWithMessage -> OnboardingStateContract.UserData.UserError(this.message)
            else -> OnboardingStateContract.UserData.UserError(GENERIC_ERROR)

        }
    }

    suspend fun confirmUsersPassword(pin: String): OnboardingStateContract.UserData {
        return when (val result = userData.confirmUsersPin(pin)) {
            is Result.Success -> OnboardingStateContract.UserData.UserConfirmed
            is Result.Error -> result.toDataError()
            else -> {
                OnboardingStateContract.UserData.UserError(GENERIC_ERROR)
            }
        }
    }
}
