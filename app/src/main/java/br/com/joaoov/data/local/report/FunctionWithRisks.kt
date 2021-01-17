package br.com.joaoov.data.local.report

import br.com.joaoov.data.local.function.Function
import br.com.joaoov.data.local.risk.Risk

data class FunctionWithRisks(
    val function: Function,
    val risks: List<Risk>
)