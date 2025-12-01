package com.ilya.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: String = "",
    val name: String,
    val genre: String = ""
)