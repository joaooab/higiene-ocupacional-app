package br.com.joaoov.data.local.syncronize

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "syncronize")
data class SyncronizeLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val createdAt: String,
    val shouldRun: Boolean
)

fun SyncronizeLocal.toModel() = Syncronize(
    createdAt = createdAt,
    shouldRun = shouldRun
)