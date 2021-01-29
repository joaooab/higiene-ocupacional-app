package br.com.joaoov.data.local.report

import br.com.joaoov.data.local.company.Company
import br.com.joaoov.data.local.company.toNetwork
import br.com.joaoov.data.remote.report.CompanyWithDepartamentsNetwork

data class CompanyWithDepartaments(
    val company: Company,
    val departamentsWithAmbients: List<DepartamentWithAmbients>
)

fun CompanyWithDepartaments.toNetwork() = CompanyWithDepartamentsNetwork(
    company.toNetwork(),
    departamentsWithAmbients.toNetwork()
)

fun List<CompanyWithDepartaments>.toNetwork() = map { it.toNetwork() }