package br.com.joaoov.data.local.resource

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResourceRisk(
    val id: String,
    val category: String,
    val name: String,
    var updatedAt: String,
    var deleted: Boolean
) : Parcelable

fun ResourceRisk.toLocal() =
    ResourceRiskLocal(
        id,
        category,
        name,
        updatedAt,
        deleted
    )

fun List<ResourceRisk>.toLocal() = map { it.toLocal() }