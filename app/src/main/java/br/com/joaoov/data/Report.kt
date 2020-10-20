package br.com.joaoov.data

import br.com.joaoov.data.ambient.Ambient
import br.com.joaoov.data.company.Company

data class Report(
    val company: Company,
    val ambients: List<Ambient>
)