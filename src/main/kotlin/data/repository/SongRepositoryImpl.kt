package com.ilya.data.repository

import com.ilya.data.datasource.Songs
import com.ilya.domain.models.Song
import com.ilya.infrastructure.config.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


class SongRepositoryImpl : SongRepository {

    override suspend fun create(song: Song): Song? = dbQuery {
        val insertStatement = Songs.insert {
            it[title] = song.title
            it[durationSeconds] = song.durationSeconds
            it[albumId] = song.albumId
            it[trackNumber] = song.trackNumber
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toSong)
    }

    override suspend fun findById(id: Int): Song? = dbQuery {
        Songs.selectAll()
            .where { Songs.id eq id }
            .map(::toSong)
            .singleOrNull()
    }

    override suspend fun findAll(): List<Song> = dbQuery {
        Songs.selectAll()
            .map(::toSong)
    }

    override suspend fun findByAlbumId(albumId: Int): List<Song> = dbQuery {
        Songs.selectAll()
            .where { Songs.albumId eq albumId }
            .map(::toSong)
    }

    override suspend fun update(id: Int, song: Song): Boolean = dbQuery {
        Songs.update({ Songs.id eq id }) {
            it[title] = song.title
            it[durationSeconds] = song.durationSeconds
            it[albumId] = song.albumId
            it[trackNumber] = song.trackNumber
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        Songs.deleteWhere { Songs.id eq id } > 0
    }

    private fun toSong(row: ResultRow): Song = Song(
        id = row[Songs.id],
        title = row[Songs.title],
        durationSeconds = row[Songs.durationSeconds],
        albumId = row[Songs.albumId],
        trackNumber = row[Songs.trackNumber]
    )
}