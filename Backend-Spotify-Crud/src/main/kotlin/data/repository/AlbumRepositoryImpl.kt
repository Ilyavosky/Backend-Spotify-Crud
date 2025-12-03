package com.ilya.data.repository

import com.ilya.data.datasource.AlbumTable
import com.ilya.domain.models.Album
import com.ilya.infrastructure.config.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.UUID

class AlbumRepositoryImpl : AlbumRepository {

    override suspend fun create(album: Album): Album? = dbQuery {
        val insertStatement = AlbumTable.insert {
            it[title] = album.title
            it[releaseYear] = album.releaseYear
            it[artistId] = UUID.fromString(album.artistId)
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toAlbum)
    }

    override suspend fun findById(id: String): Album? = dbQuery {
        AlbumTable.selectAll()
            .where { AlbumTable.id eq UUID.fromString(id) }
            .map(::toAlbum)
            .singleOrNull()
    }

    override suspend fun findAll(): List<Album> = dbQuery {
        AlbumTable.selectAll()
            .map(::toAlbum)
    }

    override suspend fun findByArtistId(artistId: String): List<Album> = dbQuery {
        AlbumTable.selectAll()
            .where { AlbumTable.artistId eq UUID.fromString(artistId) }
            .map(::toAlbum)
    }

    override suspend fun update(id: String, album: Album): Boolean = dbQuery {
        AlbumTable.update({ AlbumTable.id eq UUID.fromString(id) }) {
            it[title] = album.title
            it[releaseYear] = album.releaseYear
            it[artistId] = UUID.fromString(album.artistId)
        } > 0
    }

    override suspend fun delete(id: String): Boolean = dbQuery {
        AlbumTable.deleteWhere { AlbumTable.id eq UUID.fromString(id) } > 0
    }

    private fun toAlbum(row: ResultRow): Album = Album(
        id = row[AlbumTable.id].toString(),
        title = row[AlbumTable.title],
        releaseYear = row[AlbumTable.releaseYear],
        artistId = row[AlbumTable.artistId].toString()
    )
}