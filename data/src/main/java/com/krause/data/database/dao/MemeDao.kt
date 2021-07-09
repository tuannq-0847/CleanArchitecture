package com.krause.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krause.data.MEME_TABLE_NAME
import com.krause.data.database.model.MemeEntity

@Dao
interface MemeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun cacheMemes(memeEntities: List<MemeEntity>)

    @Query("SELECT * FROM $MEME_TABLE_NAME")
    suspend fun getCachedMemes(): List<MemeEntity>
}
