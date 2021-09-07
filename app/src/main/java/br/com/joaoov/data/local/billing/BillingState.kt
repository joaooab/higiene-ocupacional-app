package br.com.joaoov.data.local.billing

sealed class BillingState {
    object Holding : BillingState()
    class Payed(val billing: Billing) : BillingState()
    object Canceled : BillingState()
    object AlreadyOwned : BillingState()
    class Empty(val billing: Billing) : BillingState()
    object Error : BillingState()
}