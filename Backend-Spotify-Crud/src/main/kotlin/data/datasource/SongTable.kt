package com.ilya.data.datasource

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

object SongTable : Table("tracks") {
    val id = uuid("id").autoGenerate()
    val title = varchar("title", 150)
    val duration = integer("duration")
    val albumId = uuid("album_id").references(AlbumTable.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id)
}