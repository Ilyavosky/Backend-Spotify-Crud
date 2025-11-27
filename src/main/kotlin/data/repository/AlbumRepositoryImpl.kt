package com.ilya.data.repository

import com.ilya.data.datasource.Albums
import com.ilya.domain.models.Album
import com.ilya.infrastructure.config.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


class AlbumRepositoryImpl : AlbumRepository {

    override suspend fun create(album: Album): Album? = dbQuery {
        val insertStatement = Albums.insert {
            it[name] = album.name
            it[releaseYear] = album.releaseYear
            it[coverUrl] = album.coverUrl
            it[artistId] = album.artistId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toAlbum)
    }

    override suspend fun findById(id: Int): Album? = dbQuery {
        Albums.selectAll()
            .where { Albums.id eq id }
            .map(::toAlbum)
            .singleOrNull()
    }

    override suspend fun findAll(): List<Album> = dbQuery {
        Albums.selectAll()
            .map(::toAlbum)
    }

    override suspend fun findByArtistId(artistId: Int): List<Album> = dbQuery {
        Albums.selectAll()
            .where { Albums.artistId eq artistId }
            .map(::toAlbum)
    }

    override suspend fun update(id: Int, album: Album): Boolean = dbQuery {
        Albums.update({ Albums.id eq id }) {
            it[name] = album.name
            it[releaseYear] = album.releaseYear
            it[coverUrl] = album.coverUrl
            it[artistId] = album.artistId
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        Albums.deleteWhere { Albums.id eq id } > 0
    }

    private fun toAlbum(row: ResultRow): Album = Album(
        id = row[Albums.id],
        name = row[Albums.name],
        releaseYear = row[Albums.releaseYear],
        coverUrl = row[Albums.coverUrl],
        artistId = row[Albums.artistId]
    )
}