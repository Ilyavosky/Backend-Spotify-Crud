package com.ilya.data.datasource

import org.jetbrains.exposed.sql.Table

object ArtistTable : Table("artists") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val biography = text("biography")
    val imageUrl = varchar("image_url", 500)

    override val primaryKey = PrimaryKey(id)
}