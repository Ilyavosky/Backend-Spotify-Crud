package com.ilya

import com.ilya.infrastructure.config.DatabaseFactory
import com.ilya.infrastructure.config.DependencyContainer
import com.ilya.infrastructure.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init(environment.config)

    val artistService = DependencyContainer.artistService
    val albumService = DependencyContainer.albumService
    val songService = DependencyContainer.songService

    configureSerialization()
    configureCORS()
    configureLogging()
    configureStatusPages()
    configureRouting(artistService, albumService, songService)
}