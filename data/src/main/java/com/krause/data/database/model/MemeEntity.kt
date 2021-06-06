package com.krause.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.krause.data.database.MEME_TABLE_NAME

@Entity(tableName = MEME_TABLE_NAME)
class MemeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url: String,
    val template: String
)
