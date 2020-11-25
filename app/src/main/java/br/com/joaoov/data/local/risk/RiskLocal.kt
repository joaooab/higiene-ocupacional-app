package br.com.joaoov.data.local.risk

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.joaoov.data.local.function.FunctionLocal

@Entity(
    tableName = "risk",
    foreignKeys = [
    ForeignKey(
        entity = FunctionLocal::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("functionId"),
        onDelete = ForeignKey.CASCADE
    )]
)

data class RiskLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val functionId: Long = 0,
    val name: String,
    var updatedAt: String,
    var deleted: Boolean
)

fun RiskLocal.toModel() = Risk(
    id,
    functionId,
    name,
    updatedAt,
    deleted
)

fun List<RiskLocal>.toModel() = map { it.toModel() }