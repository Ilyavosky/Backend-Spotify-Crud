package com.ilya.data.datasource

import org.jetbrains.exposed.sql.Table

object ArtistTable : Table("artistas") {
    val id = uuid("id").autoGenerate()
    val name = varchar("name", 100)
    val genre = varchar("genre", 50).nullable()

    override val primaryKey = PrimaryKey(id)
}