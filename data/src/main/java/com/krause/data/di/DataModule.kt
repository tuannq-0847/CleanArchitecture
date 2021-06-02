package com.krause.data.di

import android.content.Context
import com.krause.data.networking.MemeApi
import com.krause.data.repository.MemePagingSource
import com.krause.data.repository.MemeRepositoryImpl
import com.krause.data.utils.Connectivity
import com.krause.data.utils.ConnectivityImpl
import com.krause.data.utils.Constant
import com.krause.domain.repository.MemeRepository
import com.krause.retrofitutil.RestApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideConnectivity(@ApplicationContext appContext: Context): Connectivity =
        ConnectivityImpl(appContext)

    @Singleton
    @Provides
    fun provideApiService(): MemeApi = RestApiService.create(Constant.BASE_URL)

    @Singleton
    @Provides
    fun provideRepository(memeApi: MemeApi, connectivity: Connectivity): MemeRepository =
        MemeRepositoryImpl(memeApi, connectivity)

    @Singleton
    @Provides
    fun provideMemePagingSource(memeApi: MemeApi): MemePagingSource =
        MemePagingSource(memeApi)
}
