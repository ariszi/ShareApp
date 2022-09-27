package zi.aris.data_provider.domain.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zi.aris.data_provider.data.UserRepository
import zi.aris.data_provider.domain.UserRepositoryContract
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UserDataModule {

    @Singleton
    @Provides
    fun provideUserDataStore(api:  DataStore<Preferences>): UserRepositoryContract {
        return UserRepository(api)
    }
}
