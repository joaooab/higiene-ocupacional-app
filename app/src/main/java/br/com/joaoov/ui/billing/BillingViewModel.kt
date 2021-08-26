package br.com.joaoov.ui.billing

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.State
import br.com.joaoov.data.local.billing.BillingPlan
import br.com.joaoov.data.local.billing.BillingState
import br.com.joaoov.repository.BillingRepository
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingFlowParams.ProrationMode.IMMEDIATE_WITH_TIME_PRORATION
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BillingViewModel(private val repository: BillingRepository) : ViewModel() {

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
        viewModelScope.launch(Dispatchers.IO) {
            val plans = repository.getPlans().first()
            val params = createSkuListParams(plans)
            fetchSkuDetails(params)
                .onStart {
                    _subsListState.value = State.Loading()
                }.catch {
                    _subsListState.value = State.Error(it)
                }.collect {
                    _subsListState.value = State.Success(it)
                }
        }
    }

    private fun createSkuListParams(plans: List<BillingPlan>): SkuDetailsParams {
        val skuList = plans.map { it.productId }
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)

        return params.build()
    }

    private fun fetchSkuDetails(params: SkuDetailsParams) = flow {
        emit(billingClient.querySkuDetails(params))
    }

    fun fetchPurchases() {
        viewModelScope.launch(Dispatchers.IO) {
            billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS) { billingResult, purchases ->
                handlePurchases(billingResult, purchases)
            }
        }
    }

    private fun handlePurchases(billingResult: BillingResult, purchases: List<Purchase>?) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && !purchases.isNullOrEmpty()) {
            purchases.forEach { handlePurchase(it) }
        }
//        else if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases.isNullOrEmpty()) {
//            _billingState.value = BillingState.Empty
//        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
//            _billingState.value = BillingState.Canceled
//        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
//            _billingState.value = BillingState.AlreadyOwned
//        } else {
//            _billingState.value = BillingState.Error
//        }
    }

    fun fetchBilling() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetch().onStart {
                _billingState.value = BillingState.Holding
                delay(1000)
            }.catch {
                _billingState.value = BillingState.Error
            }.collect { billing ->
                if (billing.isAvailable) {
                    _billingState.value = BillingState.Payed(billing)
                } else {
                    _billingState.value = BillingState.Empty
                }
            }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                viewModelScope.launch(Dispatchers.IO) {
                    runCatching {
                        billingClient.acknowledgePurchase(acknowledgePurchaseParams.build())
                        repository.create(purchase)
                    }.onFailure {
                        FirebaseCrashlytics.getInstance().recordException(it)
                    }
                }
            }
        }
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
        val state = billingState.value
        return if (state is BillingState.Payed) {
            state.billing.purchase
        } else {
            null
        }
    }

    fun getBillingPlan(sku: String?) = repository.getPlanByProductId(sku.orEmpty()).asLiveData()

    override fun onCleared() {
        super.onCleared()
        billingClient.endConnection()
    }

}
