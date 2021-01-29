package br.com.joaoov.data.remote.risk

data class RiskNetwork(
    val id: Long?,
    val functionId: Long?,
    val agentCategory: String?,
    val agent: String?,
    val generatedSource: String?,
    val intensity: String?,
    val actionLevel: String?,
    val tolerance: ToleranceNetwork?,
    val trajectory: String?,
    val eliminationNeutralization: String?,
    val exposure: String?,
    val methodology: String?,
    val degreeOfRisk: String?,
    val date: String?
)