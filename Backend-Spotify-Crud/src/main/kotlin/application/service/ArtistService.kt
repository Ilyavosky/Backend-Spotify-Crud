package com.ilya.application.service


import com.ilya.data.repository.ArtistRepository
import com.ilya.domain.models.Artist


class ArtistService(private val artistRepository: ArtistRepository) {

    suspend fun createArtist(artist: Artist): Artist? {
        validateArtist(artist)
        return artistRepository.create(artist)
    }

    suspend fun getArtistById(id: Int): Artist? {
        require(id > 0) { "Artist ID must be positive" }
        return artistRepository.findById(id)
    }

    suspend fun getAllArtists(): List<Artist> {
        return artistRepository.findAll()
    }

    suspend fun updateArtist(id: Int, artist: Artist): Boolean {
        require(id > 0) { "Artist ID must be positive" }
        validateArtist(artist)

        val existingArtist = artistRepository.findById(id)
            ?: throw IllegalArgumentException("Artist with ID $id not found")

        return artistRepository.update(id, artist)
    }

    suspend fun deleteArtist(id: Int): Boolean {
        require(id > 0) { "Artist ID must be positive" }

        val existingArtist = artistRepository.findById(id)
            ?: throw IllegalArgumentException("Artist with ID $id not found")

        return artistRepository.delete(id)
    }

    private fun validateArtist(artist: Artist) {
        require(artist.name.isNotBlank()) { "Artist name cannot be blank" }
        require(artist.name.length <= 255) { "Artist name is too long (max 255 characters)" }
        require(artist.biography.isNotBlank()) { "Artist biography cannot be blank" }
        require(artist.imageUrl.isNotBlank()) { "Artist image URL cannot be blank" }
        require(artist.imageUrl.length <= 500) { "Artist image URL is too long (max 500 characters)" }
    }
}