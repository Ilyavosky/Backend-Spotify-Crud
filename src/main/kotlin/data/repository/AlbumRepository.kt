package com.ilya.data.repository

import com.ilya.domain.models.Album

interface AlbumRepository {
    suspend fun create(album: Album): Album?
    suspend fun findById(id: Int): Album?
    suspend fun findAll(): List<Album>
    suspend fun findByArtistId(artistId: Int): List<Album>
    suspend fun update(id: Int, album: Album): Boolean
    suspend fun delete(id: Int): Boolean
}