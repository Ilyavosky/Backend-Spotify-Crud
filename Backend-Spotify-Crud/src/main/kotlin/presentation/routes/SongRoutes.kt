package com.ilya.presentation.routes

import com.ilya.application.service.SongService
import com.ilya.domain.models.Song
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.songRoutes(songService: SongService) {

    route("/tracks") {

        get {
            val songs = songService.getAllSongs()
            call.respond(HttpStatusCode.OK, songs)
        }

        get("/{id}") {
            val id = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val song = songService.getSongById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound, "Song not found")

            call.respond(HttpStatusCode.OK, song)
        }

        post {
            val song = call.receive<Song>()
            val createdSong = songService.createSong(song)
                ?: return@post call.respond(HttpStatusCode.InternalServerError, "Failed to create song")

            call.respond(HttpStatusCode.Created, createdSong)
        }

        put("/{id}") {
            val id = call.parameters["id"]
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val song = call.receive<Song>()
            val updated = songService.updateSong(id, song)

            if (updated) {
                call.respond(HttpStatusCode.OK, "Song updated successfully")
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Failed to update song")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val deleted = songService.deleteSong(id)

            if (deleted) {
                call.respond(HttpStatusCode.OK, "Song deleted successfully")
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Failed to delete song")
            }
        }
    }
}