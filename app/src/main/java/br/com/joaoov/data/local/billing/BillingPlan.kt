package br.com.joaoov.data.local.billing

data class BillingPlan(
    val productId: String,
    val name: String,
    val totalReport: Int,
    val price: Long,
    val deleted: Boolean
)

fun List<BillingPlan>.toLocal() = map { it.toLocal() }

fun BillingPlan.toLocal() = BillingPlanLocal(
    productId = productId,
    name = name,
    totalReport = totalReport,
    price = price,
    deleted = deleted
)