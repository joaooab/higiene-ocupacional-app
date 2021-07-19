package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import br.com.joaoov.Session
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.company.CompanyDAO
import br.com.joaoov.data.local.company.toLocal
import br.com.joaoov.data.local.company.toModel
import br.com.joaoov.data.local.report.CompanyWithDepartaments
import br.com.joaoov.data.local.report.toModel

interface CompanyRepository {

    fun getById(id: Long): Company

    fun getAll(): LiveData<List<Company>>

    suspend fun getCompanyWithRelation(company: Company): CompanyWithDepartaments

    suspend fun save(company: Company): Long

    suspend fun update(company: Company)

    suspend fun delete(company: Company)

    suspend fun getOldCompanies() : List<Company>

}

class CompanyRepositoryImpl(private val dao: CompanyDAO) :
    CompanyRepository {

    override fun getById(id: Long): Company = dao.getById(id).toModel()

    override fun getAll() = dao.getAll(Session.user.id.orEmpty()).map { it.toModel() }

    override suspend fun getCompanyWithRelation(company: Company) =
        dao.getCompanyWithRelation(company.id).toModel()

    override suspend fun save(company: Company) =
        dao.save(company.toLocal())

    override suspend fun update(company: Company) =
        dao.update(company.toLocal())

    override suspend fun delete(company: Company) =
        dao.delete(company.toLocal())

    override suspend fun getOldCompanies() = dao.getOldCompanies().map { it.toModel() }
}