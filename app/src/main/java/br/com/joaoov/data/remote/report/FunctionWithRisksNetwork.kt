package br.com.joaoov.data.remote.report

import br.com.joaoov.data.remote.function.FunctionNetwork
import br.com.joaoov.data.remote.risk.RiskNetwork

data class FunctionWithRisksNetwork(
    val function: FunctionNetwork,
    val risks: List<RiskNetwork>
)