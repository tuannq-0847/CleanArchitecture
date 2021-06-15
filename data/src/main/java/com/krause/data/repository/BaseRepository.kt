package com.krause.data.repository

import com.krause.data.utils.Connectivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class BaseRepository constructor(private val connectivity: Connectivity) {

    protected suspend fun <T> fetchData(
        dataProvider: suspend () -> Result<T>
    ): Result<T> {
        return if (connectivity.hasNetworkAccess()) {
            withContext(Dispatchers.Default) {
                dataProvider()
            }
        } else Result.failure(Exception("Error Network"))
    }

    //todo implement get from cached and also caching here.
}
