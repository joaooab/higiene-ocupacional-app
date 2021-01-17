package br.com.joaoov.data.local.report

import br.com.joaoov.data.local.ambient.Ambient

data class AmbientWithFunctions(
    val ambient: Ambient,
    val functionsWithRisks: List<FunctionWithRisks>
)