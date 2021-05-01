package com.karleinstein.sample.data.source.remote

import com.karleinstein.basemvvm.data.source.DataSource
import com.karleinstein.sample.api.MovieApi
import com.karleinstein.sample.model.MoviePopularResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val movieApi: MovieApi
) : DataSource {

    suspend fun getPopularFilms(): MoviePopularResponse = movieApi.getPopularFilms()
}
