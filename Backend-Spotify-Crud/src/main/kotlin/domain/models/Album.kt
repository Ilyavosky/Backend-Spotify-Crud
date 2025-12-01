package com.ilya.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: Int = 0,
    val name: String,
    val releaseYear: Int,
    val coverUrl: String,
    val artistId: Int
)