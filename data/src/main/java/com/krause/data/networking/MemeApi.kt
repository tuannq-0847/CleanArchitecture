package com.krause.data.networking

import com.krause.domain.model.Meme
import com.krause.retrofitutil.RestApiService
import retrofit2.http.GET

interface MemeApi : RestApiService{

    @GET("images")
    suspend fun getMemes(): List<Meme>
}
