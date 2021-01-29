package br.com.joaoov.data.local.report

import br.com.joaoov.data.local.function.Function
import br.com.joaoov.data.local.function.toNetwork
import br.com.joaoov.data.local.risk.Risk
import br.com.joaoov.data.local.risk.toNetwork
import br.com.joaoov.data.remote.report.FunctionWithRisksNetwork

data class FunctionWithRisks(
    val function: Function,
    val risks: List<Risk>
)

fun FunctionWithRisks.toNetwork() = FunctionWithRisksNetwork(
    function.toNetwork(),
    risks.toNetwork()
)

fun List<FunctionWithRisks>.toNetwork() = map { it.toNetwork() }
