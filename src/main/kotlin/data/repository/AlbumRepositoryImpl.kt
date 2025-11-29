package com.ilya.data.repository

import com.ilya.data.datasource.AlbumTable
import com.ilya.domain.models.Album
import com.ilya.infrastructure.config.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class AlbumRepositoryImpl : AlbumRepository {

    override suspend fun create(album: Album): Album? = dbQuery {
        val insertStatement = AlbumTable.insert {
            it[name] = album.name
            it[releaseYear] = album.releaseYear
            it[coverUrl] = album.coverUrl
            it[artistId] = album.artistId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toAlbum)
    }

    override suspend fun findById(id: Int): Album? = dbQuery {
        AlbumTable.selectAll()
            .where { AlbumTable.id eq id }
            .map(::toAlbum)
            .singleOrNull()
    }

    override suspend fun findAll(): List<Album> = dbQuery {
        AlbumTable.selectAll()
            .map(::toAlbum)
    }

    override suspend fun findByArtistId(artistId: Int): List<Album> = dbQuery {
        AlbumTable.selectAll()
            .where { AlbumTable.artistId eq artistId }
            .map(::toAlbum)
    }

    override suspend fun update(id: Int, album: Album): Boolean = dbQuery {
        AlbumTable.update({ AlbumTable.id eq id }) {
            it[name] = album.name
            it[releaseYear] = album.releaseYear
            it[coverUrl] = album.coverUrl
            it[artistId] = album.artistId
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        AlbumTable.deleteWhere { AlbumTable.id eq id } > 0
    }

    private fun toAlbum(row: ResultRow): Album = Album(
        id = row[AlbumTable.id],
        name = row[AlbumTable.name],
        releaseYear = row[AlbumTable.releaseYear],
        coverUrl = row[AlbumTable.coverUrl],
        artistId = row[AlbumTable.artistId]
    )
}