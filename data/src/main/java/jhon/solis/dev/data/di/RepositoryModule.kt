package jhon.solis.dev.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jhon.solis.dev.data.repository.SearchProductRepositoryImpl
import jhon.solis.dev.domain.repository.SearchProductRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    abstract fun provideBindProductRepository(productSearchRepository: SearchProductRepositoryImpl): SearchProductRepository

}