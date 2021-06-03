package com.karleinstein.sample.di

import com.karleinstein.sample.api.MovieApi
import com.karleinstein.sample.data.source.DefaultRepository
import com.karleinstein.sample.data.source.remote.RemoteDataSource
import com.krause.retrofitutil.RestApiService
import com.krause.retrofitutil.model.ApiParameter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiServices(): MovieApi = RestApiService.create(
        baseURL = "https://api.themoviedb.org/",
        apiParameter = arrayOf(
            ApiParameter(key = "api_key", name = "1f54bd990f1cdfb230adb312546d765d"),
            ApiParameter(key = "language", name = "en-US")
        )
    )

    @Singleton
    @Provides
    fun provideRemoteDataSource(movieApi: MovieApi) = RemoteDataSource(movieApi)

    @Provides
    fun provideDefaultRepository(remoteDataSource: RemoteDataSource) =
        DefaultRepository(remoteDataSource)
}
