package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.function.Function
import br.com.joaoov.data.local.function.FunctionDAO
import br.com.joaoov.data.local.function.toLocal
import br.com.joaoov.data.local.function.toModel
import br.com.joaoov.data.local.report.FunctionWithRisks
import br.com.joaoov.data.local.report.toModel

interface FunctionRepository {

    fun getById(id: Long): Function

    fun getAllByAmbient(ambient: Ambient): LiveData<List<Function>>

    suspend fun getFunctionWithRelation(function: Function): FunctionWithRisks

    suspend fun save(function: Function): Long

    suspend fun delete(function: Function)

    suspend fun update(function: Function)
}

class FunctionRepositoryImpl(private val dao: FunctionDAO) :
    FunctionRepository {

    override fun getById(id: Long) = dao.getById(id).toModel()

    override fun getAllByAmbient(ambient: Ambient): LiveData<List<Function>> =
        dao.getAllByAmbientId(ambient.id).map { it.toModel() }

    override suspend fun getFunctionWithRelation(function: Function) =
        dao.getFunctionWithRelation(function.id).toModel()

    override suspend fun save(function: Function) =
        dao.save(function.toLocal())

    override suspend fun delete(function: Function) =
        dao.delete(function.toLocal())

    override suspend fun update(function: Function) =
        dao.update(function.toLocal())

}