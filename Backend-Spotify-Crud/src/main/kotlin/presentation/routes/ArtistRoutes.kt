package com.ilya.presentation.routes

import com.ilya.application.service.AlbumService
import com.ilya.application.service.ArtistService
import com.ilya.domain.models.Artist
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.artistRoutes(artistService: ArtistService, albumService: AlbumService) {

    route("/artists") {

        get {
            val artists = artistService.getAllArtists()
            call.respond(HttpStatusCode.OK, artists)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val artist = artistService.getArtistById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound, "Artist not found")

            call.respond(HttpStatusCode.OK, artist)
        }

        post {
            val artist = call.receive<Artist>()
            val createdArtist = artistService.createArtist(artist)
                ?: return@post call.respond(HttpStatusCode.InternalServerError, "Failed to create artist")

            call.respond(HttpStatusCode.Created, createdArtist)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val artist = call.receive<Artist>()
            val updated = artistService.updateArtist(id, artist)

            if (updated) {
                call.respond(HttpStatusCode.OK, "Artist updated successfully")
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Failed to update artist")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val deleted = artistService.deleteArtist(id)

            if (deleted) {
                call.respond(HttpStatusCode.OK, "Artist deleted successfully")
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Failed to delete artist")
            }
        }

        get("/{id}/albums") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val albums = albumService.getAlbumsByArtistId(id)
            call.respond(HttpStatusCode.OK, albums)
        }
    }
}