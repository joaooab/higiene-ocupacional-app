package br.com.joaoov.data.local.resource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resource_risks")
data class ResourceRiskLocal(
    @PrimaryKey
    val id: String,
    val category: String,
    val name: String,
    var updatedAt: String,
    var deleted: Boolean
)

fun ResourceRiskLocal.toModel() =
    ResourceRisk(
        id,
        RiskCategoryResource.valueOf(category),
        name,
        updatedAt,
        deleted
    )

fun List<ResourceRiskLocal>.toModel() = map { it.toModel() }