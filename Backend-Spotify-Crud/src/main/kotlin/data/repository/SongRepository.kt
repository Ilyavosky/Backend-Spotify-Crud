package com.ilya.data.repository

import com.ilya.domain.models.Song

interface SongRepository {
    suspend fun create(song: Song): Song?
    suspend fun findById(id: Int): Song?
    suspend fun findAll(): List<Song>
    suspend fun findByAlbumId(albumId: Int): List<Song>
    suspend fun update(id: Int, song: Song): Boolean
    suspend fun delete(id: Int): Boolean
}