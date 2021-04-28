package com.karleinstein.basemvvm.api

import android.util.Log
import com.karleinstein.basemvvm.data.model.ApiParameter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

interface ApiService {

    companion object {

        inline fun <reified T : ApiService> create(
            baseURL: String,
            vararg apiParameter: ApiParameter
        ): T {
            val logger = HttpLoggingInterceptor { Log.d("API", it) }
            logger.level = HttpLoggingInterceptor.Level.BASIC
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(20L, TimeUnit.SECONDS)
                .writeTimeout(20L, TimeUnit.SECONDS)
                .connectTimeout(20L, TimeUnit.SECONDS)
                .addInterceptor(logger)
            okHttpClient.addInterceptor { chain ->
                val original = chain.request()
                val url = original.url.newBuilder()
                apiParameter.forEach {
                    url.addQueryParameter(it.key, it.name)
                }
                val builder = url.build()
                val request = original.newBuilder()
                    .url(builder)
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(T::class.java)
        }
    }
}
