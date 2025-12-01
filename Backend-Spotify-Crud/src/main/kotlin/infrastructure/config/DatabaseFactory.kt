package com.ilya.infrastructure.config

import com.ilya.data.datasource.AlbumTable
import com.ilya.data.datasource.ArtistTable
import com.ilya.data.datasource.SongTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val jdbcUrl = config.property("database.jdbcUrl").getString()
        val username = config.property("database.username").getString()
        val password = config.property("database.password").getString()

        Database.connect(createHikariDataSource(jdbcUrl, username, password))

        transaction {
            SchemaUtils.create(ArtistTable, AlbumTable, SongTable)
        }
    }

    private fun createHikariDataSource(
        jdbcUrl: String,
        username: String,
        password: String
    ): HikariDataSource {
        val config = HikariConfig().apply {
            this.jdbcUrl = jdbcUrl
            this.username = username
            this.password = password
            driverClassName = "org.postgresql.Driver"
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}