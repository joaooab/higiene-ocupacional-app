package br.com.joaoov.data.local.risk

import androidx.room.*
import br.com.joaoov.data.local.function.FunctionLocal
import br.com.joaoov.data.local.resource.ResourceAgentCategory

@Entity(
    tableName = "risk",
    foreignKeys = [
        ForeignKey(
            entity = FunctionLocal::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("functionId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("functionId")]
)
data class RiskLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val functionId: Long = 0,
    val agentCategory: String,
    val agent: String,
    val generatedSource: String,
    val intensityConcentration: String,
    val levelAction: String,
    @Embedded(prefix = "tolerance_")
    val tolerance: ToleranceLocal,
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
    ResourceAgentCategory.valueOf(agentCategory),
    agent,
    generatedSource,
    intensityConcentration,
    levelAction,
    tolerance.toModel(),
    trajectory,
    eliminationNeutralization,
    exposureMode,
    sourceMethodology,
    degreeOfRisk,
    date
)

fun List<RiskLocal>.toModel() = map { it.toModel() }