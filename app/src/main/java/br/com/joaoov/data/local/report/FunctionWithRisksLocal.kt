package br.com.joaoov.data.local.report

import androidx.room.Embedded
import androidx.room.Relation
import br.com.joaoov.data.local.function.FunctionLocal
import br.com.joaoov.data.local.function.toModel
import br.com.joaoov.data.local.risk.RiskLocal
import br.com.joaoov.data.local.risk.toModel

data class FunctionWithRisksLocal(
    @Embedded
    val function: FunctionLocal,
    @Relation(
        parentColumn = "id",
        entityColumn = "functionId",
        entity = RiskLocal::class
    )
    val risks: List<RiskLocal>?
)

fun FunctionWithRisksLocal.toModel() = FunctionWithRisks(
    function.toModel(),
    risks?.toModel().orEmpty()
)

fun List<FunctionWithRisksLocal>.toModel() = map { it.toModel() }