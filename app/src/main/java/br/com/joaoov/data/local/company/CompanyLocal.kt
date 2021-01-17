package br.com.joaoov.data.local.company

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company")
data class CompanyLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val date: String
)

fun CompanyLocal.toModel() =
    Company(
        id,
        name,
        date
    )

fun List<CompanyLocal>.toModel() = map { it.toModel() }