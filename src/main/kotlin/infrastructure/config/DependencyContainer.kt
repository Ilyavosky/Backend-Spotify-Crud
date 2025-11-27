package com.ilya.infrastructure.config

import com.ilya.application.service.AlbumService
import com.ilya.application.service.ArtistService
import com.ilya.application.service.SongService
import com.ilya.data.repository.*
git
object DependencyContainer {

    private val artistRepository: ArtistRepository = ArtistRepositoryImpl()
    private val albumRepository: AlbumRepository = AlbumRepositoryImpl()
    private val songRepository: SongRepository = SongRepositoryImpl()

    val artistService: ArtistService = ArtistService(artistRepository)

    val albumService: AlbumService = AlbumService(
        albumRepository = albumRepository,
        artistRepository = artistRepository
    )

    val songService: SongService = SongService(
        songRepository = songRepository,
        albumRepository = albumRepository
    )
}