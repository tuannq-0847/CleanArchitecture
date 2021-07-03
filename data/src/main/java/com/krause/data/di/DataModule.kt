package com.krause.data.di

import android.content.Context
import com.krause.data.database.MemeDatabase
import com.krause.data.database.dao.MemeDao
import com.krause.data.networking.MemeApi
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
    fun provideMemeDao(@ApplicationContext context: Context): MemeDao =
        MemeDatabase.getDatabase(context).memeDao()

    @Singleton
    @Provides
    fun provideRepository(
        memeApi: MemeApi,
        memeDao: MemeDao,
        connectivity: Connectivity,
        @ApplicationContext context: Context
    ): MemeRepository =
        MemeRepositoryImpl(memeApi, memeDao, connectivity, context)

//    @Singleton
//    @Provides
//    fun provideMemePagingSource(memeApi: MemeApi): MemePagingSource =
//        MemePagingSource(memeApi)
}
