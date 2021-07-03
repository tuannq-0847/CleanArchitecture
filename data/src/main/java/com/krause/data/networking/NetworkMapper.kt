package com.krause.data.networking

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (isSuccessful) body()?.run(action)
    return this
}

inline fun <T : Any> Response<T>.onFailure(action: (java.lang.Exception) -> Unit) {
    if (!isSuccessful) errorBody()?.run { action(java.lang.Exception("error...")) }
}


inline fun <T : Any> Response<T>.getData(
    cacheAction: (T) -> Unit,
    fetchFromCacheAction: () -> T
): Result<T> {
    try {
        onSuccess {
            cacheAction(it)
            return Result.success(it)
        }
        onFailure {
            val cachedModel = fetchFromCacheAction()
            if (cachedModel != null) Result.success(cachedModel) else Result.failure(Exception("mother fkcer..."))
        }
        return Result.failure(Exception("mother fkcer..."))
    } catch (e: IOException) {
        return Result.failure(Exception("mother fkcer..."))
    }
}
