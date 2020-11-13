package br.com.joaoov.data.local.resource

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResourceAgent(
    val id: String,
    val code: String,
    val name: String,
    var updatedAt: String,
    var deleted: Boolean
) : Parcelable

fun ResourceAgent.toLocal() =
    ResourceAgentLocal(
        id,
        code,
        name,
        updatedAt,
        deleted
    )

fun List<ResourceAgent>.toLocal() = map { it.toLocal() }