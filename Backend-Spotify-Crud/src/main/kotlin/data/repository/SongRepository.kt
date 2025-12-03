package com.ilya.data.repository

import com.ilya.domain.models.Song

interface SongRepository {
    suspend fun create(song: Song): Song?
    suspend fun findById(id: String): Song?
    suspend fun findAll(): List<Song>
    suspend fun findByAlbumId(albumId: String): List<Song>
    suspend fun update(id: String, song: Song): Boolean
    suspend fun delete(id: String): Boolean
}