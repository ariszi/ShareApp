package zi.aris.data_provider.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import zi.aris.data_provider.data.UserRepository
import zi.aris.data_provider.domain.UserRepositoryContract

@InstallIn(ViewModelComponent::class)
@Module
abstract class UserDataModule {

    @Binds
    abstract fun provideUserDataStore(impl: UserRepository): UserRepositoryContract
}
