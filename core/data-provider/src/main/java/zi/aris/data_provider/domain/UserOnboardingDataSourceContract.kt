package zi.aris.data_provider.domain

import kotlinx.coroutines.flow.Flow

interface UserOnboardingDataSourceContract {

    suspend fun saveUsersEmailAndPassword(email: String, password: String)

    suspend fun saveUsersInfo(firstName: String, lastName: String, telephone: String)

    suspend fun saveUsersPin(pin: String)

    suspend fun confirmUsersPin(pin: String)

    suspend fun validateUsersPin(pin: String): Flow<Result<Unit>>

    suspend fun cleanUserData()

    val usersCredentials: Flow<Result<User>>

    val usersInfo: Flow<Result<User>>

}
