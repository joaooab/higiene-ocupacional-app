package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.data.local.risk.RiskDAO
import br.com.joaoov.data.local.risk.toLocal
import br.com.joaoov.data.local.risk.toModel

interface RiskRepository {

    fun getAllByFunction(functionId: Long): LiveData<List<Risk>>

    suspend fun save(risk: Risk): Long

    suspend fun delete(risk: Risk)

    suspend fun update(risk: Risk)
}

class RiskRepositoryImpl(private val dao: RiskDAO) : RiskRepository {

    override fun getAllByFunction(functionId: Long): LiveData<List<Risk>> =
        Transformations.map(dao.getAllByFuncionId(functionId)) { it.toModel() }

    override suspend fun save(risk: Risk) =
        dao.save(risk.toLocal())

    override suspend fun delete(risk: Risk) =
        dao.delete(risk.toLocal())

    override suspend fun update(risk: Risk) =
        dao.update(risk.toLocal())

}