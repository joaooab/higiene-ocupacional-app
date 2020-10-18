package br.com.joaoov.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Departament",
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("companyId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
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