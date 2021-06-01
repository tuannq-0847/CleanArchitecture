package com.krause.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Meme(
    val id: Int,
    val bottomText: String = "",
    val image: String,
    val name: String,
    val rank: Int,
    val tags: String,
    val topText: String = ""
)
