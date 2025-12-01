package com.ilya.data.datasource

import org.jetbrains.exposed.sql.Table

object SongTable : Table("songs") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val durationSeconds = integer("duration_seconds")
    val albumId = integer("album_id").references(AlbumTable.id)
    val trackNumber = integer("track_number")

    override val primaryKey = PrimaryKey(id)
}