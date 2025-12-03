package com.ilya.data.repository

import com.ilya.data.datasource.ArtistTable
import com.ilya.domain.models.Artist
import com.ilya.infrastructure.config.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.UUID

class ArtistRepositoryImpl : ArtistRepository {

    override suspend fun create(artist: Artist): Artist? = dbQuery {
        val insertStatement = ArtistTable.insert {
            it[name] = artist.name
            it[genre] = artist.genre
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toArtist)
    }

    override suspend fun findById(id: String): Artist? = dbQuery {
        ArtistTable.selectAll()
            .where { ArtistTable.id eq UUID.fromString(id) }
            .map(::toArtist)
            .singleOrNull()
    }

    override suspend fun findAll(): List<Artist> = dbQuery {
        ArtistTable.selectAll()
            .map(::toArtist)
    }

    override suspend fun update(id: String, artist: Artist): Boolean = dbQuery {
        ArtistTable.update({ ArtistTable.id eq UUID.fromString(id) }) {
            it[name] = artist.name
            it[genre] = artist.genre
        } > 0
    }

    override suspend fun delete(id: String): Boolean = dbQuery {
        ArtistTable.deleteWhere { ArtistTable.id eq UUID.fromString(id) } > 0
    }

    private fun toArtist(row: ResultRow): Artist = Artist(
        id = row[ArtistTable.id].toString(),
        name = row[ArtistTable.name],
        genre = row[ArtistTable.genre] ?: ""
    )
}