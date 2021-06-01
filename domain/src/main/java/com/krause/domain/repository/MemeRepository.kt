package com.krause.domain.repository

import com.krause.domain.model.BaseResponse
import com.krause.domain.model.Meme

interface MemeRepository {

    fun getMemes(): BaseResponse<List<Meme>>
}
