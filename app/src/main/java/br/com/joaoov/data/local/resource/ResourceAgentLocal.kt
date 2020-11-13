package br.com.joaoov.data.local.resource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resource_agents")
data class ResourceAgentLocal(
    @PrimaryKey
    val id: String,
    val category: String,
    val name: String,
    var updatedAt: String,
    var deleted: Boolean
)

fun ResourceAgentLocal.toModel() =
    ResourceAgent(
        id,
        category,
        name,
        updatedAt,
        deleted
    )

fun List<ResourceAgentLocal>.toModel() = map { it.toModel() }