package br.com.joaoov.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.report.*
import br.com.joaoov.data.remote.report.ReportService

interface ReportRepository {

    fun getAllOfCompany(company: Company): LiveData<CompanyWithDepartaments>

    suspend fun send(report: Report)

}

class ReportRepositoryImpl(private val dao: ReportDAO, private val service: ReportService) :
    ReportRepository {

    override fun getAllOfCompany(company: Company) =
        Transformations.map(dao.getByCompany(company.id)) { it.toModel() }

    override suspend fun send(report: Report) = service.send(report.toNetwork())

}