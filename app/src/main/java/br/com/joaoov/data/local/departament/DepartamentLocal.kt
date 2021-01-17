package br.com.joaoov.data.local.departament

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.joaoov.data.local.company.CompanyLocal

@Entity(
    tableName = "departament",
    foreignKeys = [
        ForeignKey(
            entity = CompanyLocal::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("companyId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("companyId")]
)
data class DepartamentLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val companyId: Long = 0,
    val name: String,
    val date: String
)

fun DepartamentLocal.toModel() =
    Departament(
        id,
        companyId,
        name,
        date
    )

fun List<DepartamentLocal>.toModel() = map { it.toModel() }