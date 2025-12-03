package com.ilya.presentation.routes

import com.ilya.application.service.AlbumService
import com.ilya.application.service.SongService
import com.ilya.domain.models.Album
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.albumRoutes(albumService: AlbumService, songService: SongService) {

    route("/albumes") {

        get {
            val albums = albumService.getAllAlbums()
            call.respond(HttpStatusCode.OK, albums)
        }

        get("/{id}") {
            val id = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val album = albumService.getAlbumById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound, "Album not found")

            call.respond(HttpStatusCode.OK, album)
        }

        post {
            val album = call.receive<Album>()
            val createdAlbum = albumService.createAlbum(album)
                ?: return@post call.respond(HttpStatusCode.InternalServerError, "Failed to create album")

            call.respond(HttpStatusCode.Created, createdAlbum)
        }

        put("/{id}") {
            val id = call.parameters["id"]
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val album = call.receive<Album>()
            val updated = albumService.updateAlbum(id, album)

            if (updated) {
                call.respond(HttpStatusCode.OK, "Album updated successfully")
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Failed to update album")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val deleted = albumService.deleteAlbum(id)

            if (deleted) {
                call.respond(HttpStatusCode.OK, "Album deleted successfully")
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Failed to delete album")
            }
        }

        get("/{id}/tracks") {
            val id = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val songs = songService.getSongsByAlbumId(id)
            call.respond(HttpStatusCode.OK, songs)
        }
    }
}