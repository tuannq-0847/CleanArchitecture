package com.krause.domain.repository

import com.krause.domain.model.BaseResponse
import com.krause.domain.model.Meme
import kotlinx.coroutines.flow.Flow

interface MemeRepository {

    suspend fun getMemes(): Result<List<Meme>>
}
