package br.com.joaoov.data.local.risk

import android.os.Parcelable
import br.com.joaoov.data.local.resource.ResourceAgentCategory
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Risk(
    val id: Long = 0,
    val functionId: Long = 0,
    val agentType: ResourceAgentCategory,
    val agent: String,
    val generatingSource: String,
    val intensity: String,
    val actionLevel: String,
    val tolerance: Tolerance,
    val trajectory: String,
    val eliminationNeutralization: String,
    val exposure: String,
    val methodology: String,
    val degreeOfRisk: String,
    val date: String
) : Parcelable {
    @IgnoredOnParcel
    var showDetail: Boolean = false
}

fun Risk.toLocal() = RiskLocal(
    id,
    functionId,
    agentType.toString(),
    agent,
    generatingSource,
    intensity,
    actionLevel,
    tolerance.toLocal(),
    trajectory,
    eliminationNeutralization,
    exposure,
    methodology,
    degreeOfRisk,
    date
)

fun List<Risk>.toLocal() = map { it.toLocal() }