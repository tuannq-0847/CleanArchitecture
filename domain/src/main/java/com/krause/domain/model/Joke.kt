package com.krause.domain.model

data class Joke(
    val categories: List<String> = listOf(),
    val createdAt: String,
    val iconURL: String,
    val id: String,
    val url: String,
    val value: String
)
