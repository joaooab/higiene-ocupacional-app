package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.function.Function
import br.com.joaoov.data.local.function.FunctionDAO
import br.com.joaoov.data.local.function.toLocal
import br.com.joaoov.data.local.function.toModel

interface FunctionRepository {

    fun getFunctions(ambient: Ambient): LiveData<List<Function>>

    suspend fun save(function: Function): Long

    suspend fun delete(function: Function)
}

class FunctionRepositoryImpl(private val dao: FunctionDAO) :
    FunctionRepository {

    override fun getFunctions(ambient: Ambient): LiveData<List<Function>> =
        Transformations.map(dao.getAllByAmbientId(ambient.id)) { it.toModel() }

    override suspend fun save(function: Function) =
        dao.save(function.toLocal())

    override suspend fun delete(function: Function) =
        dao.delete(function.toLocal())

}