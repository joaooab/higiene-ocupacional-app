package br.com.joaoov.data.remote.report

import br.com.joaoov.data.remote.ambient.AmbientNetwork

data class AmbientWithFunctionsNetwork(
    val ambient: AmbientNetwork,
    val functionsWithRisks: List<FunctionWithRisksNetwork>
)