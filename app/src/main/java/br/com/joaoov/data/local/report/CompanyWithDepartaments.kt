package br.com.joaoov.data.local.report

import br.com.joaoov.data.local.company.Company

data class CompanyWithDepartaments(
    val company: Company,
    val departamentsWithAmbients: List<DepartamentWithAmbients>
)