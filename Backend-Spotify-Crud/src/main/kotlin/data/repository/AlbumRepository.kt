package com.ilya.data.repository

import com.ilya.domain.models.Album

interface AlbumRepository {
    suspend fun create(album: Album): Album?
    suspend fun findById(id: String): Album?
    suspend fun findAll(): List<Album>
    suspend fun findByArtistId(artistId: String): List<Album>
    suspend fun update(id: String, album: Album): Boolean
    suspend fun delete(id: String): Boolean
}