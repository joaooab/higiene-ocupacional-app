package br.com.joaoov.data.local.billing

import br.com.joaoov.Session
import br.com.joaoov.data.remote.user.isLegalEntity

data class BillingPlan(
    val productId: String,
    val name: String,
    val totalReport: Int,
    val price: Long,
    val deleted: Boolean
)

fun BillingPlan.isEntityPlan() = productId.contains("company")

fun BillingPlan.isUserPlan() = productId.contains("user")

fun List<BillingPlan>.filterByUserType() = if (Session.user.isLegalEntity()) {
    filter { it.isEntityPlan() }
} else {
    filter { it.isUserPlan() }
}

fun List<BillingPlan>.toLocal() = map { it.toLocal() }

fun BillingPlan.toLocal() = BillingPlanLocal(
    productId = productId,
    name = name,
    totalReport = totalReport,
    price = price,
    deleted = deleted
)