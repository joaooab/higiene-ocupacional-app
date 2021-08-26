package br.com.joaoov.data.remote.billing

import br.com.joaoov.data.local.billing.BillingPlan

data class BillingPlanNetwork(
    val productId: String,
    val name: String,
    val totalReport: Int,
    val price: Long,
    val deleted: Boolean
)

fun List<BillingPlanNetwork>.toModel() = map { it.toModel() }

fun BillingPlanNetwork.toModel() = BillingPlan(
    productId = productId,
    name = name,
    totalReport = totalReport,
    price = price,
    deleted = deleted
)

