package br.com.joaoov.data.local.resource

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "resource_ambients")
data class ResourceAmbientLocal(
    @PrimaryKey
    val id: String,
    val category: String,
    val name: String,
    var updatedAt: String,
    var deleted: Boolean
)

fun ResourceAmbientLocal.toModel() =
    ResourceAmbient(
        id,
        category,
        name,
        updatedAt,
        deleted
    )

fun List<ResourceAmbientLocal>.toModel() = map { it.toModel() }