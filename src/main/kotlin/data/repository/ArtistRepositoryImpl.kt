package com.ilya.data.repository

import com.ilya.data.datasource.Artists
import com.ilya.domain.models.Artist
import com.ilya.infrastructure.config.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


class ArtistRepositoryImpl : ArtistRepository {

    override suspend fun create(artist: Artist): Artist? = dbQuery {
        val insertStatement = Artists.insert {
            it[name] = artist.name
            it[biography] = artist.biography
            it[imageUrl] = artist.imageUrl
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toArtist)
    }

    override suspend fun findById(id: Int): Artist? = dbQuery {
        Artists.selectAll()
            .where { Artists.id eq id }
            .map(::toArtist)
            .singleOrNull()
    }

    override suspend fun findAll(): List<Artist> = dbQuery {
        Artists.selectAll()
            .map(::toArtist)
    }

    override suspend fun update(id: Int, artist: Artist): Boolean = dbQuery {
        Artists.update({ Artists.id eq id }) {
            it[name] = artist.name
            it[biography] = artist.biography
            it[imageUrl] = artist.imageUrl
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        Artists.deleteWhere { Artists.id eq id } > 0
    }

    private fun toArtist(row: ResultRow): Artist = Artist(
        id = row[Artists.id],
        name = row[Artists.name],
        biography = row[Artists.biography],
        imageUrl = row[Artists.imageUrl]
    )
}