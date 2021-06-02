package com.krause.data.repository

import com.krause.data.networking.MemeApi
import com.krause.data.utils.Connectivity
import com.krause.domain.model.Meme
import com.krause.domain.repository.MemeRepository
import javax.inject.Inject

class MemeRepositoryImpl @Inject constructor(
    private val memeApi: MemeApi,
    connectivity: Connectivity
) : BaseRepository(connectivity), MemeRepository {
    override suspend fun getMemes(): Result<List<Meme>> {
        return fetchData(
            dataProvider = {
                try {
                    Result.success(memeApi.getMemes())
                } catch (throwable: Throwable) {
                    Result.failure(throwable)
                }
            }
        )
    }
}
