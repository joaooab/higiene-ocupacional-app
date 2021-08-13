package br.com.joaoov.ui.billing

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.State
import br.com.joaoov.data.remote.billing.BillingState
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingFlowParams.ProrationMode.IMMEDIATE_WITH_TIME_PRORATION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BillingViewModel : ViewModel() {

    private val _subsListState = MutableStateFlow<State<SkuDetailsResult>>(State.Loading())
    val subsListState: StateFlow<State<SkuDetailsResult>> = _subsListState
    private val _billingState = MutableStateFlow<BillingState>(BillingState.Holding)
    val billingState: StateFlow<BillingState> = _billingState
    var skuSelected: SkuDetails? = null
    private lateinit var billingClient: BillingClient

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            handlePurchases(billingResult, purchases)
        }

    fun init(context: Context) {
        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        startConnection()
    }

    private fun startConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                startConnection()
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    fetchSubs()
                }
            }
        })
    }

    fun fetchSubs() {
        viewModelScope.launch {
            val params = createSkuListParams()
            fetchSkuDetails(params).flowOn(Dispatchers.IO)
                .onStart {
                    _subsListState.value = State.Loading()
                }.catch {
                    _subsListState.value = State.Error(it)
                }.collect {
                    _subsListState.value = State.Success(it)
                }
        }
    }

    private fun createSkuListParams(): SkuDetailsParams {
        val skuList = arrayListOf("1_signature_user_plan", "2_signature_user_plan")
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)

        return params.build()
    }

    private fun fetchSkuDetails(params: SkuDetailsParams) = flow {
        emit(billingClient.querySkuDetails(params))
    }

    fun fetchPurchases() {
        viewModelScope.launch {
            billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS) { billingResult, purchases ->
                handlePurchases(billingResult, purchases)
            }
        }
    }

    private fun handlePurchases(billingResult: BillingResult, purchases: List<Purchase>?) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && !purchases.isNullOrEmpty()) {
            purchases.forEach { handlePurchase(it) }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            _billingState.value = BillingState.Canceled
        } else {
            _billingState.value = BillingState.Error
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                viewModelScope.launch {
                    billingClient.acknowledgePurchase(acknowledgePurchaseParams.build())
                }

            }
        }
        _billingState.value = BillingState.Purchased(purchase)
    }

    fun contract(activity: Activity, skuSelected: SkuDetails) {
        val currentPurchase = getCurrentPurchase()
        if (currentPurchase == null) {
            val flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuSelected)
                .build()
            billingClient.launchBillingFlow(activity, flowParams)
        } else {
            val flowParams = BillingFlowParams.newBuilder()
                .setSubscriptionUpdateParams(
                    BillingFlowParams.SubscriptionUpdateParams.newBuilder()
                        .setOldSkuPurchaseToken(currentPurchase.purchaseToken)
                        .setReplaceSkusProrationMode(IMMEDIATE_WITH_TIME_PRORATION)
                        .build()
                )
                .setSkuDetails(skuSelected)
                .build()

            billingClient.launchBillingFlow(activity, flowParams)
        }

    }

    private fun getCurrentPurchase(): Purchase? {
        val billingState = billingState.value
        return if (billingState is BillingState.Purchased) {
            billingState.purchase
        } else {
            null
        }
    }

    override fun onCleared() {
        super.onCleared()
        billingClient.endConnection()
    }

}
