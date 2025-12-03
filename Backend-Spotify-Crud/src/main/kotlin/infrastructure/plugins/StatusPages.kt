package com.ilya.infrastructure.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<IllegalArgumentException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "Bad Request")
        }

        exception<IllegalStateException> { call, cause ->
            call.respond(HttpStatusCode.Conflict, cause.message ?: "Conflict")
        }

        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "Internal Server Error")
        }
    }
}