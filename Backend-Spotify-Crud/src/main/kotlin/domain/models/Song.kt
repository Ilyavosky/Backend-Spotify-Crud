package com.ilya.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val id: String = "",
    val title: String,
    val duration: Int,
    val albumId: String
)