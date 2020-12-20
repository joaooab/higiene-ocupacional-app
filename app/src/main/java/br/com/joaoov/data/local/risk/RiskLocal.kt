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
    val riskFactor: String,
    val generatingSource: String,
    val intensityConcentration: String,
    val levelAction: String,
    val NR15: String,
    val ACGIH: String,
    val trajectory: String,
    val eliminationNeutralization: String,
    val exposureMode: String,
    val sourceMethodology: String,
    val degreeOfRisk: String,
    val date: String
)

fun RiskLocal.toModel() = Risk(
    id,
    functionId,
    riskFactor,
    generatingSource,
    intensityConcentration,
    levelAction,
    NR15,
    ACGIH,
    trajectory,
    eliminationNeutralization,
    exposureMode,
    sourceMethodology,
    degreeOfRisk,
    date
)

fun List<RiskLocal>.toModel() = map { it.toModel() }