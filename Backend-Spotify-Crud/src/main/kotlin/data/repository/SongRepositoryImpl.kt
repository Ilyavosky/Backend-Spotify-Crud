package com.ilya.data.repository

import com.ilya.data.datasource.SongTable
import com.ilya.domain.models.Song
import com.ilya.infrastructure.config.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class SongRepositoryImpl : SongRepository {

    override suspend fun create(song: Song): Song? = dbQuery {
        val insertStatement = SongTable.insert {
            it[title] = song.title
            it[durationSeconds] = song.durationSeconds
            it[albumId] = song.albumId
            it[trackNumber] = song.trackNumber
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toSong)
    }

    override suspend fun findById(id: Int): Song? = dbQuery {
        SongTable.selectAll()
            .where { SongTable.id eq id }
            .map(::toSong)
            .singleOrNull()
    }

    override suspend fun findAll(): List<Song> = dbQuery {
        SongTable.selectAll()
            .map(::toSong)
    }

    override suspend fun findByAlbumId(albumId: Int): List<Song> = dbQuery {
        SongTable.selectAll()
            .where { SongTable.albumId eq albumId }
            .map(::toSong)
    }

    override suspend fun update(id: Int, song: Song): Boolean = dbQuery {
        SongTable.update({ SongTable.id eq id }) {
            it[title] = song.title
            it[durationSeconds] = song.durationSeconds
            it[albumId] = song.albumId
            it[trackNumber] = song.trackNumber
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        SongTable.deleteWhere { SongTable.id eq id } > 0
    }

    private fun toSong(row: ResultRow): Song = Song(
        id = row[SongTable.id],
        title = row[SongTable.title],
        durationSeconds = row[SongTable.durationSeconds],
        albumId = row[SongTable.albumId],
        trackNumber = row[SongTable.trackNumber]
    )
}