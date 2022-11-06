package zi.aris.data_provider

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import zi.aris.data_provider.data.UserRepository
import zi.aris.data_provider.data.UserRepository.Companion.USER_EMAIL
import zi.aris.data_provider.data.UserRepository.Companion.USER_PASSWORD

@ExperimentalCoroutinesApi
internal class UserDataProviderTest {


    @Test
    fun `should save user credentials`() = runTest {
        val preferences = mockk<MutablePreferences>(relaxed = true) {
            every { toMutablePreferences() } returns this
        }
        val dataStore = mockk<DataStore<Preferences>>
        {
            coEvery { updateData(captureCoroutine()) } coAnswers {
                coroutine<suspend (Preferences) -> Unit>().coInvoke(preferences)
                preferences
            }
        }

        val storage = createStorage(dataStore)

        storage.saveUsersEmailAndPassword(email = "email@email.com", password = "fvjdnf34")

        verify { preferences[USER_EMAIL] = "email@email.com" }
        verify { preferences[USER_PASSWORD] = "fvjdnf34" }
    }

    private fun createStorage(dataStore: DataStore<Preferences>): UserRepository {
        return UserRepository(dataStore)
    }

}
