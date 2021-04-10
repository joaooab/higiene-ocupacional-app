package br.com.joaoov.data.local.risk

import android.os.Parcelable
import br.com.joaoov.data.local.resource.ResourceAgentCategory
import br.com.joaoov.data.remote.risk.RiskNetwork
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Risk(
    val id: Long = 0,
    val functionId: Long = 0,
    val agentCategory: ResourceAgentCategory,
    val agent: String,
    val generatedSource: String,
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
    agentCategory.toString(),
    agent,
    generatedSource,
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


fun Risk.toNetwork() = RiskNetwork(
    id,
    functionId,
    agentCategory.toString(),
    agent,
    generatedSource,
    intensity,
    actionLevel,
    tolerance.toNetwork(),
    trajectory,
    eliminationNeutralization,
    exposure,
    methodology,
    degreeOfRisk,
    date
)

fun List<Risk>.toNetwork() = map { it.toNetwork() }