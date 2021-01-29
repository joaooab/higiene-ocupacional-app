package br.com.joaoov.data.local.report

import br.com.joaoov.data.local.departament.Departament
import br.com.joaoov.data.local.departament.toNetwork
import br.com.joaoov.data.remote.report.DepartamentWithAmbientsNetwork

data class DepartamentWithAmbients(
    val departament: Departament,
    val ambientsWithFunctions: List<AmbientWithFunctions>
)

fun DepartamentWithAmbients.toNetwork() = DepartamentWithAmbientsNetwork(
    departament.toNetwork(),
    ambientsWithFunctions.toNetwork()
)

fun List<DepartamentWithAmbients>.toNetwork() = map { it.toNetwork() }

