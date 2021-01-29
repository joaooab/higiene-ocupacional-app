package br.com.joaoov.data.local.report

import br.com.joaoov.data.local.ambient.Ambient
import br.com.joaoov.data.local.ambient.toNetwork
import br.com.joaoov.data.remote.report.AmbientWithFunctionsNetwork

data class AmbientWithFunctions(
    val ambient: Ambient,
    val functionsWithRisks: List<FunctionWithRisks>
)

fun AmbientWithFunctions.toNetwork() = AmbientWithFunctionsNetwork(
    ambient.toNetwork(),
    functionsWithRisks.toNetwork()
)

fun List<AmbientWithFunctions>.toNetwork() = map { it.toNetwork() }
