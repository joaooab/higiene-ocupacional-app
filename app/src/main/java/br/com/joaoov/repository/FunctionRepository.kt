package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.joaoov.data.ambient.Ambient
import br.com.joaoov.data.function.Function
import br.com.joaoov.data.function.FunctionDAO
import br.com.joaoov.data.function.toLocal
import br.com.joaoov.data.function.toModel

interface FunctionRepository {

    fun getFunctions(ambient: Ambient): LiveData<List<Function>>

    suspend fun save(function: Function): Long

}

class FunctionRepositoryImpl(private val dao: FunctionDAO) :
    FunctionRepository {

    override fun getFunctions(ambient: Ambient): LiveData<List<Function>> =
        Transformations.map(dao.getAllByAmbientId(ambient.id)) { it.toModel() }


    override suspend fun save(function: Function) =
        dao.save(function.toLocal())

}