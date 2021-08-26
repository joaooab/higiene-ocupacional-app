package br.com.joaoov.data.local.billing

sealed class BillingState {
    object Holding : BillingState()
    class Payed(val billing: Billing) : BillingState()
    object Canceled : BillingState()
    object AlreadyOwned : BillingState()
    object Empty : BillingState()
    object Error : BillingState()
}