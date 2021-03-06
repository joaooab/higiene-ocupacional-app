package br.com.joaoov.data.remote.resource

import br.com.joaoov.data.local.resource.ResourceRisk
import br.com.joaoov.data.local.resource.ResourceRiskCategory

data class ResourceRiskNetwork(
    var _id: String?,
    var category: String?,
    var name: String?,
    var updatedAt: String?,
    var deleted: Boolean?
)

fun List<ResourceRiskNetwork>.toModel() = map { it.toModel() }

fun ResourceRiskNetwork.toModel() = ResourceRisk(
    _id.orEmpty(),
    ResourceRiskCategory.valueOf(category.orEmpty()),
    name.orEmpty(),
    updatedAt.orEmpty(),
    deleted ?: false
)