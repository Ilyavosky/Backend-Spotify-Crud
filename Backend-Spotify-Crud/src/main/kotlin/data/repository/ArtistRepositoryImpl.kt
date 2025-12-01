package com.ilya.data.repository

import com.ilya.data.datasource.ArtistTable
import com.ilya.domain.models.Artist
import com.ilya.infrastructure.config.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ArtistRepositoryImpl : ArtistRepository {

    override suspend fun create(artist: Artist): Artist? = dbQuery {
        val insertStatement = ArtistTable.insert {
            it[name] = artist.name
            it[biography] = artist.biography
            it[imageUrl] = artist.imageUrl
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toArtist)
    }

    override suspend fun findById(id: Int): Artist? = dbQuery {
        ArtistTable.selectAll()
            .where { ArtistTable.id eq id }
            .map(::toArtist)
            .singleOrNull()
    }

    override suspend fun findAll(): List<Artist> = dbQuery {
        ArtistTable.selectAll()
            .map(::toArtist)
    }

    override suspend fun update(id: Int, artist: Artist): Boolean = dbQuery {
        ArtistTable.update({ ArtistTable.id eq id }) {
            it[name] = artist.name
            it[biography] = artist.biography
            it[imageUrl] = artist.imageUrl
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        ArtistTable.deleteWhere { ArtistTable.id eq id } > 0
    }

    private fun toArtist(row: ResultRow): Artist = Artist(
        id = row[ArtistTable.id],
        name = row[ArtistTable.name],
        biography = row[ArtistTable.biography],
        imageUrl = row[ArtistTable.imageUrl]
    )
}