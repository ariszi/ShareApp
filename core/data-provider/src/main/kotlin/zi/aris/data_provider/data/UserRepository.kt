package zi.aris.data_provider.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import zi.aris.data_provider.domain.*
import javax.inject.Inject


class UserRepository @Inject constructor(private val dataStore: DataStore<Preferences>) :
    UserRepositoryContract {

    companion object {

        internal val USER_EMAIL = stringPreferencesKey("user_email")
        internal val USER_PASSWORD = stringPreferencesKey("user_password")
        internal val USER_FIRST_NAME = stringPreferencesKey("user_first_name")
        internal val USER_LAST_NAME = stringPreferencesKey("user_last_name")
        internal val USER_TELEPHONE = stringPreferencesKey("user_telephone")
        internal val USER_PIN = stringPreferencesKey("user_pin")
        internal val USER_PIN_CONFIRMED = booleanPreferencesKey("user_pin_confirmed")

        internal const val USER_NOT_CONFIRMED = "The pin you have entered doesn't match with your previous input"
        internal const val PIN_NOT_VALID = "Pin is not valid. Please try again"

    }

    override suspend fun saveUsersEmailAndPassword(email: String, password: String) {
        dataStore.edit {
            it[USER_EMAIL] = email
            it[USER_PASSWORD] = password
        }
    }

    override suspend fun saveUsersInfo(firstName: String, lastName: String, telephone: String) {
        dataStore.edit {
            it[USER_FIRST_NAME] = firstName
            it[USER_LAST_NAME] = lastName
            it[USER_TELEPHONE] = telephone
        }
    }

    override suspend fun saveUsersPin(pin: String) {
        dataStore.edit {
            it[USER_PIN] = pin
        }
    }

    override suspend fun confirmUsersPin(pin: String): Result<Unit> {
        val usersInsertedPin = dataStore.data.first()[USER_PIN] ?: ""
        var isConfirmed: Boolean = false
        dataStore.edit {
            isConfirmed = usersInsertedPin == pin
            it[USER_PIN_CONFIRMED] = isConfirmed
        }
        return if (isConfirmed) {
            Result.Success(Unit)
        } else {
            ErrorWithMessage(USER_NOT_CONFIRMED)
        }
    }


    override suspend fun validateUsersPin(pin: String): Result<Unit> {
        val storedPin = dataStore.data.first()[USER_PIN] ?: ""
        return if (storedPin == pin) {
            Result.Success(Unit)
        } else
            ErrorWithMessage(PIN_NOT_VALID)
    }

    override suspend fun cleanUserData() {
        dataStore.edit { it.clear() }
    }

    override suspend fun cleanUsersPin() {
        dataStore.edit { it.remove(USER_PIN) }
    }

    override fun usersCredentials(): Flow<Result<User>> {
        return dataStore.data.map { preferences ->
            Result.Success(User(email = preferences[USER_EMAIL] ?: "", password = preferences[USER_EMAIL] ?: ""))
        }.catch { cause: Throwable -> cause.message?.let { ErrorWithMessage(it) } ?: GenericError }
    }

    override suspend fun isUserRegistered(): Boolean {
        return dataStore.data.first()[USER_PIN_CONFIRMED] ?: false
    }

    override fun usersInfo(): Flow<Result<User>> {
        return dataStore.data.map { preferences ->
            Result.Success(
                User(
                    name = preferences[USER_FIRST_NAME] ?: "",
                    lastName = preferences[USER_LAST_NAME] ?: "",
                    telephone = preferences[USER_TELEPHONE] ?: "",
                    email = preferences[USER_EMAIL] ?: ""
                )
            )
        }.catch { cause: Throwable -> cause.message?.let { ErrorWithMessage(it) } ?: GenericError }
    }
}
