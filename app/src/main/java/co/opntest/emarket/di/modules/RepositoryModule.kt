package co.opntest.emarket.di.modules

import co.opntest.emarket.data.remote.services.ApiService
import co.opntest.emarket.data.repositories.RepositoryImpl
import co.opntest.emarket.domain.repositories.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideRepository(apiService: ApiService): Repository = RepositoryImpl(apiService)
}
