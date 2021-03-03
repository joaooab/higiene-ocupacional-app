package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import br.com.joaoov.data.local.resource.*
import br.com.joaoov.data.remote.resource.ResourceService
import br.com.joaoov.data.remote.resource.toModel

interface ResourceRepository {

    suspend fun fetchAllAmbientResources(updateAt: String?)

    suspend fun fetchAllRisksResources(updateAt: String?)

    suspend fun fetchAllAgentsResources(updateAt: String?)

    fun getAmbentResourcesByCategory(category: ResourceAmbientCategory): LiveData<List<ResourceAmbient>>

    fun getRiskResourcesByCategory(category: ResourceRiskCategory): LiveData<List<ResourceRisk>>

    fun getAgentResourcesByCategory(category: ResourceAgentCategory): LiveData<List<ResourceAgent>>

    suspend fun clearAmbients()

    suspend fun clearRisks()

    suspend fun clearAgents()

}

class ResourceRepositoryImpl(
    private val resorceAmbientDAO: ResourceAmbientDAO,
    private val resourceRiskDAO: ResourceRiskDAO,
    private val resourceAgentDAO: ResourceAgentDAO,
    private val service: ResourceService
) : ResourceRepository {

    override suspend fun fetchAllAmbientResources(updateAt: String?) {
        val resources = service.fetchAllAmbientResources(updateAt).map { it.toModel() }
        val resourcesDeleted = resources.filter { it.deleted }
        resorceAmbientDAO.save(resources.toLocal())
        resorceAmbientDAO.delete(resourcesDeleted.toLocal())
    }

    override suspend fun fetchAllRisksResources(updateAt: String?) {
        val resources = service.fetchAllRisksResources(updateAt).map { it.toModel() }
        val resourcesDeleted = resources.filter { it.deleted }
        resourceRiskDAO.save(resources.toLocal())
        resourceRiskDAO.delete(resourcesDeleted.toLocal())
    }

    override suspend fun fetchAllAgentsResources(updateAt: String?) {
        val resources = service.fetchAllAgentsResources(updateAt).map { it.toModel() }
        val resourcesDeleted = resources.filter { it.deleted }
        resourceAgentDAO.save(resources.toLocal())
        resourceAgentDAO.delete(resourcesDeleted.toLocal())
    }

    override fun getAmbentResourcesByCategory(category: ResourceAmbientCategory): LiveData<List<ResourceAmbient>> =
        resorceAmbientDAO.getAllByCategory(category.toString()).map { it.toModel() }

    override fun getRiskResourcesByCategory(category: ResourceRiskCategory): LiveData<List<ResourceRisk>> =
        resourceRiskDAO.getAllByCategory(category.toString()).map { it.toModel() }

    override fun getAgentResourcesByCategory(category: ResourceAgentCategory): LiveData<List<ResourceAgent>> =
        resourceAgentDAO.getAllByCategory(category.toString()).map { it.toModel() }

    override suspend fun clearAmbients() = resorceAmbientDAO.clear()

    override suspend fun clearRisks() = resourceRiskDAO.clear()

    override suspend fun clearAgents() = resourceAgentDAO.clear()

}
