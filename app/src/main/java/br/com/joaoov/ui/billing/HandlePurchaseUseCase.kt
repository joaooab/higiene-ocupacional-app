package br.com.joaoov.ui.billing

import android.util.Log
import br.com.joaoov.Session
import br.com.joaoov.data.remote.user.UserPlan
import br.com.joaoov.data.remote.user.isAdmin
import com.android.billingclient.api.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

interface HandlePurchaseUseCase {

    suspend operator fun invoke(
        client: BillingClient,
        billingResult: BillingResult,
        purchases: Collection<Purchase>?,
        emit: (UserPlan?) -> Unit
    )
}

class HandlePurchaseUseCaseImpl : HandlePurchaseUseCase {

    private lateinit var client: BillingClient
    private lateinit var emit: (UserPlan?) -> Unit

    override suspend fun invoke(
        client: BillingClient,
        billingResult: BillingResult,
        purchases: Collection<Purchase>?,
        emit: (UserPlan?) -> Unit,
    ) {
        Log.i("billing", "HandlePurchaseUseCase init")
        this.client = client
        this.emit = emit

        Log.i("billing", "HandlePurchaseUseCase isAdmin ${Session.user.isAdmin()}")
        if (Session.user.isAdmin()) {
            Log.i("billing", "HandlePurchaseUseCase isAdmin")
            this.emit(UserPlan.Basic())
            return
        }
        this.client = client
        handlePurchases(billingResult, purchases)
    }

    private suspend fun handlePurchases(
        billingResult: BillingResult,
        purchases: Collection<Purchase>?
    ) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK
            && !purchases.isNullOrEmpty()
        ) {
            purchases.forEach { handlePurchase(it) }
        } else {
            Log.i("billing", "handlePurchase, NotPayed")
            emit(null)
        }
    }

    private suspend fun handlePurchase(purchase: Purchase) {
        Log.i("billing", "handlePurchase")
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                acknowledgePurchase(purchase)
            }
            Log.i("billing", "handlePurchase, Payed2")
            emit(UserPlan.Basic(purchase.purchaseToken))
        }
    }

    private suspend fun acknowledgePurchase(purchase: Purchase) {
        coroutineScope {
            val acknowledgePurchaseParams = AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
            this.launch { client.acknowledgePurchase(acknowledgePurchaseParams.build()) }
        }
    }
}
