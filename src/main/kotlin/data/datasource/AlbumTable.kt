package com.ilya.data.datasource

import org.jetbrains.exposed.sql.Table

object AlbumTable : Table("albums") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val releaseYear = integer("release_year")
    val coverUrl = varchar("cover_url", 500)
    val artistId = integer("artist_id").references(ArtistTable.id)

    override val primaryKey = PrimaryKey(id)
}