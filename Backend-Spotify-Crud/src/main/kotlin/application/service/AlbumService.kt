package com.ilya.application.service

import com.ilya.data.repository.AlbumRepository
import com.ilya.data.repository.ArtistRepository
import com.ilya.domain.models.Album

class AlbumService(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository
) {

    suspend fun createAlbum(album: Album): Album? {
        validateAlbum(album)
        validateArtistExists(album.artistId)
        return albumRepository.create(album)
    }

    suspend fun getAlbumById(id: String): Album? {
        return albumRepository.findById(id)
    }

    suspend fun getAllAlbums(): List<Album> {
        return albumRepository.findAll()
    }

    suspend fun getAlbumsByArtistId(artistId: String): List<Album> {
        validateArtistExists(artistId)
        return albumRepository.findByArtistId(artistId)
    }

    suspend fun updateAlbum(id: String, album: Album): Boolean {
        validateAlbum(album)
        validateArtistExists(album.artistId)
        val existingAlbum = albumRepository.findById(id)
            ?: throw IllegalArgumentException("Album with ID $id not found")
        return albumRepository.update(id, album)
    }

    suspend fun deleteAlbum(id: String): Boolean {
        val existingAlbum = albumRepository.findById(id)
            ?: throw IllegalArgumentException("Album with ID $id not found")
        return albumRepository.delete(id)
    }

    private fun validateAlbum(album: Album) {
        require(album.title.isNotBlank()) { "Album title cannot be blank" }
        require(album.title.length <= 150) { "Album title is too long (max 150 characters)" }
        require(album.releaseYear >= 1900) { "Release year must be after 1900" }
    }

    private suspend fun validateArtistExists(artistId: String) {
        val artist = artistRepository.findById(artistId)
            ?: throw IllegalArgumentException("Artist with ID $artistId not found")
    }
}