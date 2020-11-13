package br.com.joaoov.data.remote

import br.com.joaoov.data.local.resource.ResourceAgent

data class ResourceAgentNetwork(
    var _id: String?,
    val code: String?,
    val name: String?,
    val updatedAt: String?,
    var deleted: Boolean?
)

fun List<ResourceAgentNetwork>.toModel() = map { it.toModel() }

fun ResourceAgentNetwork.toModel() = ResourceAgent(
    _id.orEmpty(),
    code.orEmpty(),
    name.orEmpty(),
    updatedAt.orEmpty(),
    deleted ?: false
)
