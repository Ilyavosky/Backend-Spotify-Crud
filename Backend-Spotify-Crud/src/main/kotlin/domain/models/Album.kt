package com.ilya.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: String = "",
    val title: String,
    val releaseYear: Int,
    val artistId: String
)