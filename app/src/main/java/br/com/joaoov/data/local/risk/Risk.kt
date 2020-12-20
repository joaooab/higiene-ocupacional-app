package br.com.joaoov.data.local.risk

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Risk(
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
): Parcelable

fun Risk.toLocal() = RiskLocal(
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

fun List<Risk>.toLocal() = map { it.toLocal() }