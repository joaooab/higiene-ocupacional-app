package br.com.joaoov.data.remote.billing

import com.android.billingclient.api.Purchase

sealed class BillingState {
    object Holding: BillingState()
    class Purchased(val purchase: Purchase) : BillingState()
    object Canceled: BillingState()
    object Error: BillingState()
}