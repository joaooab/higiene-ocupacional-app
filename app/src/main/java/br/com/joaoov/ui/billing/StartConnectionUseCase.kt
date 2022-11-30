package br.com.joaoov.ui.billing

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PurchasesUpdatedListener

interface StartConnectionUseCase {

    suspend operator fun invoke(
        listener: PurchasesUpdatedListener,
        onSetupFinished: () -> Unit,
    ): BillingClient

}

class StartConnectionUseCaseImpl(private val context: Context) : StartConnectionUseCase {

    override suspend fun invoke(
        listener: PurchasesUpdatedListener,
        onSetupFinished: () -> Unit,
    ): BillingClient = BillingClient.newBuilder(context)
        .setListener(listener)
        .enablePendingPurchases()
        .build()
        .apply {
            startConnection(this, onSetupFinished)
        }

    private fun startConnection(client: BillingClient, onSetupFinished: () -> Unit) {
        client.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                startConnection(client, onSetupFinished)
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                onSetupFinished()
            }
        })
    }
}
