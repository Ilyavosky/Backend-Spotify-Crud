package com.ilya.application.service

import com.ilya.data.repository.AlbumRepository
import com.ilya.data.repository.SongRepository
import com.ilya.domain.models.Song

class SongService(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository
) {

    suspend fun createSong(song: Song): Song? {
        validateSong(song)
        validateAlbumExists(song.albumId)
        return songRepository.create(song)
    }

    suspend fun getSongById(id: String): Song? {
        return songRepository.findById(id)
    }

    suspend fun getAllSongs(): List<Song> {
        return songRepository.findAll()
    }

    suspend fun getSongsByAlbumId(albumId: String): List<Song> {
        validateAlbumExists(albumId)
        return songRepository.findByAlbumId(albumId)
    }

    suspend fun updateSong(id: String, song: Song): Boolean {
        validateSong(song)
        validateAlbumExists(song.albumId)
        val existingSong = songRepository.findById(id)
            ?: throw IllegalArgumentException("Song with ID $id not found")
        return songRepository.update(id, song)
    }

    suspend fun deleteSong(id: String): Boolean {
        val existingSong = songRepository.findById(id)
            ?: throw IllegalArgumentException("Song with ID $id not found")
        return songRepository.delete(id)
    }

    private fun validateSong(song: Song) {
        require(song.title.isNotBlank()) { "Song title cannot be blank" }
        require(song.title.length <= 150) { "Song title is too long (max 150 characters)" }
        require(song.duration > 0) { "Song duration must be positive" }
    }

    private suspend fun validateAlbumExists(albumId: String) {
        val album = albumRepository.findById(albumId)
            ?: throw IllegalArgumentException("Album with ID $albumId not found")
    }
}