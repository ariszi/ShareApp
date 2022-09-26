package zi.aris.data_provider.domain.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import zi.aris.data_provider.data.UserRepository
import zi.aris.data_provider.domain.UserRepositoryContract

@InstallIn(ViewModelComponent::class)
@Module
class UserDataModule {

    @ViewModelScoped
    @Provides
    fun provideUserDataStore(api:  DataStore<Preferences>): UserRepositoryContract {
        return UserRepository(api)
    }
}
