package com.ilya.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val id: Int = 0,
    val title: String,
    val durationSeconds: Int,
    val albumId: Int,
    val trackNumber: Int
)