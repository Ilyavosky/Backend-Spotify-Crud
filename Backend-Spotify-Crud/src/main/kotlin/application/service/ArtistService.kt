package com.ilya.application.service

import com.ilya.data.repository.ArtistRepository
import com.ilya.domain.models.Artist

class ArtistService(private val artistRepository: ArtistRepository) {

    suspend fun createArtist(artist: Artist): Artist? {
        validateArtist(artist)
        return artistRepository.create(artist)
    }

    suspend fun getArtistById(id: String): Artist? {
        return artistRepository.findById(id)
    }

    suspend fun getAllArtists(): List<Artist> {
        return artistRepository.findAll()
    }

    suspend fun updateArtist(id: String, artist: Artist): Boolean {
        validateArtist(artist)
        val existingArtist = artistRepository.findById(id)
            ?: throw IllegalArgumentException("Artist with ID $id not found")
        return artistRepository.update(id, artist)
    }

    suspend fun deleteArtist(id: String): Boolean {
        val existingArtist = artistRepository.findById(id)
            ?: throw IllegalArgumentException("Artist with ID $id not found")
        return artistRepository.delete(id)
    }

    private fun validateArtist(artist: Artist) {
        require(artist.name.isNotBlank()) { "Artist name cannot be blank" }
        require(artist.name.length <= 100) { "Artist name is too long (max 100 characters)" }
    }
}