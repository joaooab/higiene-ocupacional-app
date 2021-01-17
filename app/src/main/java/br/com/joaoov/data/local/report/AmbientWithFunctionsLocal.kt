package br.com.joaoov.data.local.report

import androidx.room.Embedded
import androidx.room.Relation
import br.com.joaoov.data.local.ambient.AmbientLocal
import br.com.joaoov.data.local.ambient.toModel
import br.com.joaoov.data.local.function.FunctionLocal

data class AmbientWithFunctionsLocal(
    @Embedded
    val ambient: AmbientLocal,
    @Relation(
        parentColumn = "id",
        entityColumn = "ambientId",
        entity = FunctionLocal::class
    )
    val functionsWithRisks: List<FunctionWithRisksLocal>?
)

fun AmbientWithFunctionsLocal.toModel() = AmbientWithFunctions(
    ambient.toModel(),
    functionsWithRisks?.toModel().orEmpty()
)

fun List<AmbientWithFunctionsLocal>.toModel() = map { it.toModel() }