package com.krause.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.krause.data.MEME_TABLE_NAME
import com.krause.domain.model.Meme

@Entity(tableName = MEME_TABLE_NAME)
class MemeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "template")
    val template: String
)

fun List<MemeEntity>.convertToMemes() = mutableListOf<Meme>().apply {
    this@convertToMemes.forEach {
        this.add(Meme(url = it.url, template = it.template))
    }
}
