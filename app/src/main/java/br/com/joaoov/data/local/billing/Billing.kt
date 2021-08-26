package br.com.joaoov.data.local.billing

import com.android.billingclient.api.Purchase

data class Billing(
    val productId: String = DEFAULT_PRODUCT,
    val purchase: Purchase?,
    val reportCount: Int = 0,
    val isAvailable: Boolean
) {
    companion object {
        const val DEFAULT_PRODUCT = "trial"
    }
}
