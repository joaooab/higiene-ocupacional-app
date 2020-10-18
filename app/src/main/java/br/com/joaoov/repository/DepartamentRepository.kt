package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.joaoov.data.*

interface DepartamentRepository {

    fun getDepartaments(company: Company): LiveData<List<Departament>>

    suspend fun save(departament: Departament): Long

}

class DepartamentRepositoryImpl(private val dao: DepartamentDAO) :
    DepartamentRepository {

    override fun getDepartaments(company: Company): LiveData<List<Departament>> =
        Transformations.map(dao.getAllByCompanyId(company.id)) { it.toModel() }


    override suspend fun save(departament: Departament) =
        dao.save(departament.toLocal())

}