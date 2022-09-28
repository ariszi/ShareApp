package zi.aris.login

import zi.aris.data_provider.domain.ErrorWithMessage
import zi.aris.data_provider.domain.Result
import zi.aris.data_provider.domain.UserRepositoryContract
import javax.inject.Inject

class UserValidationUsecase @Inject constructor(private val userData: UserRepositoryContract) {

    companion object {

        const val GENERIC_ERROR = "Wow. Sorry that never happened before"

    }


    suspend fun validateUser(pin: String): LoginContract.UserData {
        return when (val result = userData.validateUsersPin(pin)) {
            is Result.Success -> LoginContract.UserData.UserIsValid
            is Result.Error -> result.toDataError()
            else -> {
                LoginContract.UserData.UserError(GENERIC_ERROR)
            }
        }
    }

    private fun Result.Error.toDataError(): LoginContract.UserData.UserError {
        return when (this) {
            is ErrorWithMessage -> LoginContract.UserData.UserError(this.message)
            else -> LoginContract.UserData.UserError(GENERIC_ERROR)

        }
    }
}
