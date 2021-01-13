package br.com.joaoov.data.local.resource

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResourceAmbient(
    val id: String,
    val category: String,
    val name: String,
    var updatedAt: String,
    var deleted: Boolean
) : Parcelable {

    override fun toString(): String {
        return name
    }
}

fun ResourceAmbient.toLocal() =
    ResourceAmbientLocal(
        id,
        category,
        name,
        updatedAt,
        deleted
    )

fun List<ResourceAmbient>.toLocal() = map { it.toLocal() }