package com.karleinstein.sample.api

import com.karleinstein.basemvvm.api.ApiService
import com.karleinstein.sample.model.MoviePopularResponse
import retrofit2.http.GET

interface MovieApi : ApiService {
    @GET("3/movie/popular")
    suspend fun getPopularFilms(): MoviePopularResponse
}
