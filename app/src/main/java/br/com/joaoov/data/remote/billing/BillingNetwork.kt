package br.com.joaoov.data.remote.billing

import br.com.joaoov.data.local.billing.Billing
import com.android.billingclient.api.Purchase

data class BillingNetwork(
    val productId: String,
    val purchase: Purchase?,
    val reportCount: Int = 0,
    val isAvailable: Boolean
)

fun BillingNetwork.toModel() = Billing(
    productId = productId,
    purchase = purchase,
    reportCount = reportCount,
    isAvailable = isAvailable
)