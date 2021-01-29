package br.com.joaoov.data.remote.report

import br.com.joaoov.data.remote.company.CompanyNetwork

data class CompanyWithDepartamentsNetwork(
    val company: CompanyNetwork,
    val departamentsWithAmbients: List<DepartamentWithAmbientsNetwork>
)