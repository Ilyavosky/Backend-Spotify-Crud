package com.ilya.data.repository

import com.ilya.domain.models.Artist

interface ArtistRepository {
    suspend fun create(artist: Artist): Artist?
    suspend fun findById(id: String): Artist?
    suspend fun findAll(): List<Artist>
    suspend fun update(id: String, artist: Artist): Boolean
    suspend fun delete(id: String): Boolean
}