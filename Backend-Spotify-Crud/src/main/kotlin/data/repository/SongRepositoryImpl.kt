package com.ilya.data.repository

import com.ilya.data.datasource.SongTable
import com.ilya.domain.models.Song
import com.ilya.infrastructure.config.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.UUID

class SongRepositoryImpl : SongRepository {

    override suspend fun create(song: Song): Song? = dbQuery {
        val insertStatement = SongTable.insert {
            it[title] = song.title
            it[duration] = song.duration
            it[albumId] = UUID.fromString(song.albumId)
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toSong)
    }

    override suspend fun findById(id: String): Song? = dbQuery {
        SongTable.selectAll()
            .where { SongTable.id eq UUID.fromString(id) }
            .map(::toSong)
            .singleOrNull()
    }

    override suspend fun findAll(): List<Song> = dbQuery {
        SongTable.selectAll()
            .map(::toSong)
    }

    override suspend fun findByAlbumId(albumId: String): List<Song> = dbQuery {
        SongTable.selectAll()
            .where { SongTable.albumId eq UUID.fromString(albumId) }
            .map(::toSong)
    }

    override suspend fun update(id: String, song: Song): Boolean = dbQuery {
        SongTable.update({ SongTable.id eq UUID.fromString(id) }) {
            it[title] = song.title
            it[duration] = song.duration
            it[albumId] = UUID.fromString(song.albumId)
        } > 0
    }

    override suspend fun delete(id: String): Boolean = dbQuery {
        SongTable.deleteWhere { SongTable.id eq UUID.fromString(id) } > 0
    }

    private fun toSong(row: ResultRow): Song = Song(
        id = row[SongTable.id].toString(),
        title = row[SongTable.title],
        duration = row[SongTable.duration],
        albumId = row[SongTable.albumId].toString()
    )
}