package com.ilya.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: Int = 0,
    val name: String,
    val biography: String,
    val imageUrl: String
)