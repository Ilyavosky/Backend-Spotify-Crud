package com.ilya.infrastructure.plugins

import com.ilya.application.service.AlbumService
import com.ilya.application.service.ArtistService
import com.ilya.application.service.SongService
import com.ilya.presentation.routes.albumRoutes
import com.ilya.presentation.routes.artistRoutes
import com.ilya.presentation.routes.songRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    artistService: ArtistService,
    albumService: AlbumService,
    songService: SongService
) {
    routing {
        get("/") {
            call.respondText("Spotify Backend API - Running!")
        }

        route("/api") {
            artistRoutes(artistService, albumService)
            albumRoutes(albumService, songService)
            songRoutes(songService)
        }
    }
}