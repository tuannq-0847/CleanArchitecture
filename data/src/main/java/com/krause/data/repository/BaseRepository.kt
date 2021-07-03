package com.krause.data.repository

import android.accounts.NetworkErrorException
import com.krause.data.database.model.MemeEntity
import com.krause.data.utils.Connectivity
import com.krause.domain.model.Meme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository constructor(private val connectivity: Connectivity) {

    protected suspend fun <T> fetchData(
        dataProvider: suspend () -> Result<T>
    ): Result<T> {
        return if (connectivity.hasNetworkAccess()) {
            withContext(Dispatchers.IO) {
                dataProvider()
            }
        } else Result.failure(Exception("Error Network"))
    }

    protected suspend fun <T> fetchDataWithCached(
        dataProvider: suspend () -> Result<T>,
        cacheProvider: suspend () -> T
    ): Result<T> {
        return if (connectivity.hasNetworkAccess()) {
            withContext(Dispatchers.IO) {
                dataProvider()
            }
        } else {
            withContext(Dispatchers.IO) {
                val result = cacheProvider()
                if (result != null) Result.success(result) else Result.failure(
                    NetworkErrorException(
                        "Cache Error"
                    )
                )
            }
        }
    }
}

fun List<Meme>.convertToMemeEntities() = mutableListOf<MemeEntity>().apply {
    this@convertToMemeEntities.forEach {
        this.add(MemeEntity(url = it.url, template = it.template,id = 0))
    }
}
