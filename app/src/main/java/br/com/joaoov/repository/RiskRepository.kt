package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.joaoov.data.local.agent.Agent
import br.com.joaoov.data.local.resource.ResourceAgent
import br.com.joaoov.data.local.resource.ResourceAgentDAO
import br.com.joaoov.data.local.resource.ResourceAgentLocal
import br.com.joaoov.data.local.resource.toModel
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.data.local.risk.RiskDao
import br.com.joaoov.data.local.risk.toLocal
import br.com.joaoov.data.local.risk.toModel

interface RiskRepository {

    fun getRisks(functionId: Long): LiveData<List<Risk>>

    suspend fun save(risk: Risk): Long

    suspend fun delete(risk: Risk)

    fun getAgents(agent: String): LiveData<List<ResourceAgent>>
}

class RiskRepositoryImpl(
    private val dao: RiskDao,
    private val agentDAO: ResourceAgentDAO
) : RiskRepository {
    override fun getRisks(functionId: Long): LiveData<List<Risk>> =
        Transformations.map(dao.getAllByFuncionId(functionId)) { it.toModel() }

    override suspend fun save(risk: Risk): Long =
        dao.save(risk.toLocal())

    override suspend fun delete(risk: Risk) =
        dao.delete(risk.toLocal())

    override fun getAgents(agent: String): LiveData<List<ResourceAgent>> {
        return Transformations.map(agentDAO.getAllByCategory(agent)) {
            it.toModel()
        }
    }
}