package br.com.joaoov.data.local.report

import br.com.joaoov.data.remote.report.ReportNetwork

data class Report(
    var email: String = "",
    var data: CompanyWithDepartaments? = null
)

fun Report.toNetwork() = ReportNetwork(
    email = email,
    data = data?.toNetwork()
)