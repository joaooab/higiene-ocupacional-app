package br.com.joaoov.data.remote.resource

import br.com.joaoov.data.local.resource.ResourceAmbient

data class ResourceAmbientNetwork(
    var _id: String?,
    var category: String?,
    var name: String?,
    var updatedAt: String?,
    var deleted: Boolean?
)

fun List<ResourceAmbientNetwork>.toModel() = map { it.toModel() }

fun ResourceAmbientNetwork.toModel() = ResourceAmbient(
    _id.orEmpty(),
    category.orEmpty(),
    name.orEmpty(),
    updatedAt.orEmpty(),
    deleted ?: false
)