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

    suspend fun getAlbumById(id: Int): Album? {
        require(id > 0) { "Album ID must be positive" }
        return albumRepository.findById(id)
    }

    suspend fun getAllAlbums(): List<Album> {
        return albumRepository.findAll()
    }

    suspend fun getAlbumsByArtistId(artistId: Int): List<Album> {
        require(artistId > 0) { "Artist ID must be positive" }
        validateArtistExists(artistId)
        return albumRepository.findByArtistId(artistId)
    }

    suspend fun updateAlbum(id: Int, album: Album): Boolean {
        require(id > 0) { "Album ID must be positive" }
        validateAlbum(album)
        validateArtistExists(album.artistId)

        val existingAlbum = albumRepository.findById(id)
            ?: throw IllegalArgumentException("Album with ID $id not found")

        return albumRepository.update(id, album)
    }

    suspend fun deleteAlbum(id: Int): Boolean {
        require(id > 0) { "Album ID must be positive" }

        val existingAlbum = albumRepository.findById(id)
            ?: throw IllegalArgumentException("Album with ID $id not found")

        return albumRepository.delete(id)
    }

    private fun validateAlbum(album: Album) {
        require(album.name.isNotBlank()) { "Album name cannot be blank" }
        require(album.name.length <= 255) { "Album name is too long (max 255 characters)" }
        require(album.releaseYear > 1900) { "Release year must be after 1900" }
        require(album.releaseYear <= 2100) { "Release year cannot be in the distant future" }
        require(album.coverUrl.isNotBlank()) { "Album cover URL cannot be blank" }
        require(album.coverUrl.length <= 500) { "Album cover URL is too long (max 500 characters)" }
        require(album.artistId > 0) { "Artist ID must be positive" }
    }

    private suspend fun validateArtistExists(artistId: Int) {
        val artist = artistRepository.findById(artistId)
            ?: throw IllegalArgumentException("Artist with ID $artistId not found")
    }
}