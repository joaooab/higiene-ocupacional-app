package br.com.joaoov.data.local.report

import androidx.room.Embedded
import androidx.room.Relation
import br.com.joaoov.data.local.company.CompanyLocal
import br.com.joaoov.data.local.company.toModel
import br.com.joaoov.data.local.departament.DepartamentLocal

data class CompanyWithDepartamentsLocal(
    @Embedded
    val company: CompanyLocal,
    @Relation(
        parentColumn = "id",
        entityColumn = "companyId",
        entity = DepartamentLocal::class
    )
    val departamentsWithAmbients: List<DepartamentWithAmbientsLocal>?
)


fun CompanyWithDepartamentsLocal.toModel() = CompanyWithDepartaments(
    company.toModel(),
    departamentsWithAmbients?.toModel().orEmpty()
)

fun List<CompanyWithDepartamentsLocal>.toModel() = map { it.toModel() }