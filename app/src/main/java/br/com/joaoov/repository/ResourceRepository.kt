package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import br.com.joaoov.data.local.resource.*
import br.com.joaoov.data.remote.ResourceService
import br.com.joaoov.data.remote.toModel

interface ResourceRepository {

    suspend fun fetchAllAmbientResources(updateAt: String?)

    suspend fun fetchAllRisksResources(updateAt: String?)

    suspend fun fetchAllAgentsResources(updateAt: String?)

    fun getAmbentResourcesByCategory(category: ResourceAmbientCategory): LiveData<List<ResourceAmbientLocal>>

}

class ResourceRepositoryImpl(
    private val ambientDao: ResourceAmbientDAO,
    private val riskDao: ResourceRiskDAO,
    private val agentDao: ResourceAgentDAO,
    private val service: ResourceService
) : ResourceRepository {

    override suspend fun fetchAllAmbientResources(updateAt: String?) {
        val resources = service.fetchAllAmbientResources(updateAt).map { it.toModel() }
        ambientDao.save(resources.toLocal())
    }

    override suspend fun fetchAllRisksResources(updateAt: String?) {
        val resources = service.fetchAllRisksResources(updateAt).map { it.toModel() }
        riskDao.save(resources.toLocal())
    }

    override suspend fun fetchAllAgentsResources(updateAt: String?) {
        val resources = service.fetchAllAgentsResources(updateAt).map { it.toModel() }
        agentDao.save(resources.toLocal())
    }

    override fun getAmbentResourcesByCategory(category: ResourceAmbientCategory): LiveData<List<ResourceAmbientLocal>> =
        ambientDao.getAllByCategory(category.toString())

}
