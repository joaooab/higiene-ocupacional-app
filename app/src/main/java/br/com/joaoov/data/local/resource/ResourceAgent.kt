package br.com.joaoov.data.local.resource

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResourceAgent(
    val id: String,
    val category: ResourceAgentCategory,
    val code: String,
    val name: String,
    var updatedAt: String,
    var deleted: Boolean
) : Parcelable {
    override fun toString(): String {
        return if (category.isESocial()) {
            "$code - $name"
        } else {
            name
        }
    }
}

fun ResourceAgent.toLocal() =
    ResourceAgentLocal(
        id,
        category.toString(),
        code,
        name,
        updatedAt,
        deleted
    )

fun List<ResourceAgent>.toLocal() = map { it.toLocal() }