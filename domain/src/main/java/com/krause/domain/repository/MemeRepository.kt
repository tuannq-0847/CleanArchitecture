package com.krause.domain.repository

import android.graphics.Bitmap
import com.krause.domain.model.Meme
import kotlinx.coroutines.flow.Flow

interface MemeRepository {

    suspend fun getMemes(): Result<List<Meme>>

    suspend fun saveImage(bitmap: Bitmap): Result<Boolean>
}
