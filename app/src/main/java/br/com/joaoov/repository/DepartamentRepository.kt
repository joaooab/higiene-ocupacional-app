package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.data.local.departament.DepartamentDAO
import br.com.joaoov.data.local.departament.toLocal
import br.com.joaoov.data.local.departament.toModel

interface DepartamentRepository {

    fun getById(id: Long): Departament

    fun getAllByCompany(company: Company): LiveData<List<Departament>>

    suspend fun save(departament: Departament)

    suspend fun delete(departament: Departament)

    suspend fun update(departament: Departament)
}

class DepartamentRepositoryImpl(private val dao: DepartamentDAO) :
    DepartamentRepository {

    override fun getById(id: Long): Departament = dao.getById(id).toModel()

    override fun getAllByCompany(company: Company) =
        dao.getAllByCompanyId(company.id).map { it.toModel() }

    override suspend fun save(departament: Departament) =
        dao.save(departament.toLocal())

    override suspend fun delete(departament: Departament) =
        dao.delete(departament.toLocal())

    override suspend fun update(departament: Departament) =
        dao.update(departament.toLocal())

}