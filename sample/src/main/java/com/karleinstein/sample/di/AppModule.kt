package com.karleinstein.sample.di

import com.karleinstein.basemvvm.api.ApiService
import com.karleinstein.basemvvm.data.model.ApiParameter
import com.karleinstein.sample.api.MovieApi
import com.karleinstein.sample.data.source.DefaultRepository
import com.karleinstein.sample.data.source.remote.RemoteDataSource
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
    fun provideApiServices(): MovieApi = ApiService.create(
        "https://api.themoviedb.org/",
        ApiParameter(key = "api_key", name = "1f54bd990f1cdfb230adb312546d765d"),
        ApiParameter(key = "language", name = "en-US")
    )

    @Singleton
    @Provides
    fun provideRemoteDataSource(movieApi: MovieApi) = RemoteDataSource(movieApi)

    @Provides
    fun provideDefaultRepository(remoteDataSource: RemoteDataSource) = DefaultRepository(remoteDataSource)
}
