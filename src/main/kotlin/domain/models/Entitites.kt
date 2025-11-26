package com.ilya.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: Int = 0,
    val name: String,
    val biography: String,
    val imageUrl: String
)


@Serializable
data class Album(
    val id: Int = 0,
    val name: String,
    val releaseYear: Int,
    val coverUrl: String,
    val artistId: Int
)


@Serializable
data class Song(
    val id: Int = 0,
    val title: String,
    val durationSeconds: Int,
    val albumId: Int,
    val trackNumber: Int
)