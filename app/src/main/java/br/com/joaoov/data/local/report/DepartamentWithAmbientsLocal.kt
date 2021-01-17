package br.com.joaoov.data.local.report

import androidx.room.Embedded
import androidx.room.Relation
import br.com.joaoov.data.local.ambient.AmbientLocal
import br.com.joaoov.data.local.departament.DepartamentLocal
import br.com.joaoov.data.local.departament.toModel


data class DepartamentWithAmbientsLocal(
    @Embedded
    val departament: DepartamentLocal,
    @Relation(
        parentColumn = "id",
        entityColumn = "departamentId",
        entity = AmbientLocal::class
    )
    val ambientsWithFunctions: List<AmbientWithFunctionsLocal>?
)


fun DepartamentWithAmbientsLocal.toModel() = DepartamentWithAmbients(
    departament.toModel(),
    ambientsWithFunctions?.toModel().orEmpty()
)

fun List<DepartamentWithAmbientsLocal>.toModel() = map { it.toModel() }

