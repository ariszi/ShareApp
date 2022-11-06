package zi.aris.user

import kotlinx.coroutines.flow.first
import zi.aris.data_provider.domain.Result
import zi.aris.data_provider.domain.User
import zi.aris.data_provider.domain.UserRepositoryContract
import javax.inject.Inject

class UserProfileUsecase @Inject constructor(private val userData: UserRepositoryContract) {

    companion object {

        const val GENERIC_ERROR = "Wow. Sorry that never happened before"

    }

    suspend fun signOut() {
        userData.cleanUserData()
    }

    suspend fun getUserInfo(): UserProfileContract.UserData {
        return when (val result = userData.usersInfo().first()) {
            is Result.Success -> result.data.toUserInfo()
            else -> {
                UserProfileContract.UserData.UserError(GENERIC_ERROR)
            }
        }
    }

    private fun User.toUserInfo() = UserProfileContract.UserData.UserInfo(name, lastName, email)
}
