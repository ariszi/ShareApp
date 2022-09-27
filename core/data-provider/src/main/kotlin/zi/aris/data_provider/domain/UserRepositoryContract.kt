package zi.aris.data_provider.domain

import kotlinx.coroutines.flow.Flow


interface UserRepositoryContract {

    suspend fun saveUsersEmailAndPassword(email: String, password: String)

    suspend fun saveUsersInfo(firstName: String, lastName: String, telephone: String)

    suspend fun saveUsersPin(pin: String)

    suspend fun confirmUsersPin(pin: String): Flow<Result<Unit>>

    suspend fun validateUsersPin(pin: String): Flow<Result<Unit>>

    suspend fun isUserRegistered(): Boolean

    suspend fun cleanUserData()

    suspend fun usersCredentials(): Flow<Result<User>>

    suspend fun usersInfo(): Flow<Result<User>>

}
