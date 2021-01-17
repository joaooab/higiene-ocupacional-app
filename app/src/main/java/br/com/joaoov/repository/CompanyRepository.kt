package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.company.CompanyDAO
import br.com.joaoov.data.local.company.toLocal
import br.com.joaoov.data.local.company.toModel

interface CompanyRepository {

    fun getAll(): LiveData<List<Company>>

    suspend fun save(company: Company)

    suspend fun update(company: Company)

    suspend fun delete(company: Company)

}

class CompanyRepositoryImpl(private val dao: CompanyDAO) :
    CompanyRepository {

    override fun getAll() = dao.getAll().map { it.toModel() }

    override suspend fun save(company: Company) =
        dao.save(company.toLocal())

    override suspend fun update(company: Company) =
        dao.update(company.toLocal())

    override suspend fun delete(company: Company) =
        dao.delete(company.toLocal())

}