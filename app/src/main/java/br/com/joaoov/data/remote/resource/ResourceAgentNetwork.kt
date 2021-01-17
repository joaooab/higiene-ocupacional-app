package br.com.joaoov.data.remote.resource

import br.com.joaoov.data.local.resource.ResourceAgent
import br.com.joaoov.data.local.resource.ResourceAgentCategory

data class ResourceAgentNetwork(
    var _id: String?,
    val category: String?,
    val code: String?,
    val name: String?,
    val updatedAt: String?,
    var deleted: Boolean?
)

fun List<ResourceAgentNetwork>.toModel() = map { it.toModel() }

fun ResourceAgentNetwork.toModel() = ResourceAgent(
    _id.orEmpty(),
    ResourceAgentCategory.valueOf(category.orEmpty()),
    code.orEmpty(),
    name.orEmpty(),
    updatedAt.orEmpty(),
    deleted ?: false
)
