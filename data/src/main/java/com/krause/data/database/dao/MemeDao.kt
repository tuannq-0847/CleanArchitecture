package com.krause.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.krause.data.database.MEME_TABLE_NAME
import com.krause.data.database.model.MemeEntity
import com.krause.domain.model.Meme

@Dao
interface MemeDao {

    @Insert
    @JvmSuppressWildcards
    suspend fun cacheMemes(memeEntities: List<MemeEntity>)

    @Query("SELECT * FROM $MEME_TABLE_NAME")
    suspend fun getCachedMemes(): List<MemeEntity>
}
