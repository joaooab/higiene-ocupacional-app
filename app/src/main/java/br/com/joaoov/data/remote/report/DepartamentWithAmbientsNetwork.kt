package br.com.joaoov.data.remote.report

import br.com.joaoov.data.remote.departament.DepartamentNetwork

data class DepartamentWithAmbientsNetwork(
    val departament: DepartamentNetwork,
    val ambientsWithFunctions: List<AmbientWithFunctionsNetwork>
)
