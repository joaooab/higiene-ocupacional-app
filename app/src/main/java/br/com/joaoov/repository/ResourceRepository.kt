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

}

class ResourceRepositoryImpl(
    private val resorceAmbientDao: ResourceAmbientDAO,
    private val resourceRiskDao: ResourceRiskDAO,
    private val resourceAgentDao: ResourceAgentDAO,
    private val service: ResourceService
) : ResourceRepository {

    override suspend fun fetchAllAmbientResources(updateAt: String?) {
        val resources = service.fetchAllAmbientResources(updateAt).map { it.toModel() }
        resorceAmbientDao.save(resources.toLocal())
    }

    override suspend fun fetchAllRisksResources(updateAt: String?) {
        val resources = service.fetchAllRisksResources(updateAt).map { it.toModel() }
        resourceRiskDao.save(resources.toLocal())
    }

    override suspend fun fetchAllAgentsResources(updateAt: String?) {
        val resources = service.fetchAllAgentsResources(updateAt).map { it.toModel() }
        resourceAgentDao.save(resources.toLocal())
    }

    override fun getAmbentResourcesByCategory(category: ResourceAmbientCategory): LiveData<List<ResourceAmbient>> =
        resorceAmbientDao.getAllByCategory(category.toString()).map { it.toModel() }

    override fun getRiskResourcesByCategory(category: ResourceRiskCategory): LiveData<List<ResourceRisk>> =
        resourceRiskDao.getAllByCategory(category.toString()).map { it.toModel() }

    override fun getAgentResourcesByCategory(category: ResourceAgentCategory): LiveData<List<ResourceAgent>> =
        resourceAgentDao.getAllByCategory(category.toString()).map { it.toModel() }

}
