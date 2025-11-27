package com.ilya.data.datasource

import org.jetbrains.exposed.sql.Table

object Artists : Table("artists") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val biography = text("biography")
    val imageUrl = varchar("image_url", 500)

    override val primaryKey = PrimaryKey(id)
}

object Albums : Table("albums") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val releaseYear = integer("release_year")
    val coverUrl = varchar("cover_url", 500)
    val artistId = integer("artist_id").references(Artists.id)

    override val primaryKey = PrimaryKey(id)
}

object Songs : Table("songs") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val durationSeconds = integer("duration_seconds")
    val albumId = integer("album_id").references(Albums.id)
    val trackNumber = integer("track_number")

    override val primaryKey = PrimaryKey(id)
}