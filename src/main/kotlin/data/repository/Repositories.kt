package com.ilya.data.repository

import com.ilya.domain.models.Album
import com.ilya.domain.models.Artist
import com.ilya.domain.models.Song


interface ArtistRepository {
    suspend fun create(artist: Artist): Artist?
    suspend fun findById(id: Int): Artist?
    suspend fun findAll(): List<Artist>
    suspend fun update(id: Int, artist: Artist): Boolean
    suspend fun delete(id: Int): Boolean
}


interface AlbumRepository {
    suspend fun create(album: Album): Album?
    suspend fun findById(id: Int): Album?
    suspend fun findAll(): List<Album>
    suspend fun findByArtistId(artistId: Int): List<Album>
    suspend fun update(id: Int, album: Album): Boolean
    suspend fun delete(id: Int): Boolean
}


interface SongRepository {
    suspend fun create(song: Song): Song?
    suspend fun findById(id: Int): Song?
    suspend fun findAll(): List<Song>
    suspend fun findByAlbumId(albumId: Int): List<Song>
    suspend fun update(id: Int, song: Song): Boolean
    suspend fun delete(id: Int): Boolean
}