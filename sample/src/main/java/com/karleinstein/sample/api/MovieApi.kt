package com.karleinstein.sample.api

import com.karleinstein.sample.model.MoviePopularResponse
import com.krause.retrofitutil.RestApiService
import retrofit2.http.GET

interface MovieApi : RestApiService {
    @GET("3/movie/popular")
    suspend fun getPopularFilms(): MoviePopularResponse
}
