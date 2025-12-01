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

    suspend fun getSongById(id: Int): Song? {
        require(id > 0) { "Song ID must be positive" }
        return songRepository.findById(id)
    }

    suspend fun getAllSongs(): List<Song> {
        return songRepository.findAll()
    }

    suspend fun getSongsByAlbumId(albumId: Int): List<Song> {
        require(albumId > 0) { "Album ID must be positive" }
        validateAlbumExists(albumId)
        return songRepository.findByAlbumId(albumId)
    }

    suspend fun updateSong(id: Int, song: Song): Boolean {
        require(id > 0) { "Song ID must be positive" }
        validateSong(song)
        validateAlbumExists(song.albumId)

        val existingSong = songRepository.findById(id)
            ?: throw IllegalArgumentException("Song with ID $id not found")

        return songRepository.update(id, song)
    }

    suspend fun deleteSong(id: Int): Boolean {
        require(id > 0) { "Song ID must be positive" }

        val existingSong = songRepository.findById(id)
            ?: throw IllegalArgumentException("Song with ID $id not found")

        return songRepository.delete(id)
    }

    private fun validateSong(song: Song) {
        require(song.title.isNotBlank()) { "Song title cannot be blank" }
        require(song.title.length <= 255) { "Song title is too long (max 255 characters)" }
        require(song.durationSeconds > 0) { "Song duration must be positive" }
        require(song.durationSeconds <= 7200) { "Song duration is too long (max 2 hours)" }
        require(song.trackNumber > 0) { "Track number must be positive" }
        require(song.trackNumber <= 999) { "Track number is too high (max 999)" }
        require(song.albumId > 0) { "Album ID must be positive" }
    }

    private suspend fun validateAlbumExists(albumId: Int) {
        val album = albumRepository.findById(albumId)
            ?: throw IllegalArgumentException("Album with ID $albumId not found")
    }
}