package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.data.local.departament.DepartamentDAO
import br.com.joaoov.data.local.departament.toLocal
import br.com.joaoov.data.local.departament.toModel

interface DepartamentRepository {

    fun getDepartaments(company: Company): LiveData<List<Departament>>

    suspend fun save(departament: Departament): Long

    suspend fun delete(departament: Departament)

}

class DepartamentRepositoryImpl(private val dao: DepartamentDAO) :
    DepartamentRepository {

    override fun getDepartaments(company: Company): LiveData<List<Departament>> =
        Transformations.map(dao.getAllByCompanyId(company.id)) { it.toModel() }

    override suspend fun save(departament: Departament) =
        dao.save(departament.toLocal())

    override suspend fun delete(departament: Departament) =
        dao.delete(departament.toLocal())

}