package com.krause.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Meme(
    val url: String,
    val template: String
)
