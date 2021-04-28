package com.karleinstein.sample.data.source

import com.karleinstein.basemvvm.data.source.Repository
import com.karleinstein.sample.data.source.remote.RemoteDataSource
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {
    suspend fun getPopularFilms() = remoteDataSource.getPopularFilms()
}
