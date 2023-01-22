package br.com.joaoov.ui.billing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.com.joaoov.Session
import br.com.joaoov.data.State
import br.com.joaoov.data.remote.user.UserPlan
import br.com.joaoov.data.remote.user.isAdmin
import br.com.joaoov.repository.BillingRepository
import com.android.billingclient.api.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BillingViewModel(
    private val repository: BillingRepository,
    private val startConnectionUseCase: StartConnectionUseCase,
    private val fetchPlansUseCase: FetchPlansUseCase,
    private val handlePurchaseUseCase: HandlePurchaseUseCase,
    private val contractPlanUseCase: ContractPlanUseCase
) : ViewModel() {

    private val _plans = MutableStateFlow<State<SkuDetailsResult>>(State.Loading())
    val plans = _plans.asStateFlow()
    private val _userPlan = MutableStateFlow<UserPlan?>(null)
    val userPlan = _userPlan.asStateFlow()
    var skuSelected: SkuDetails? = null
    private lateinit var client: BillingClient

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if (purchases.isNullOrEmpty()) return@PurchasesUpdatedListener
            handlePurchases(billingResult, purchases)
        }

    init {
        viewModelScope.launch {
            client = startConnectionUseCase(purchasesUpdatedListener) {
                fetchPlans()
                fetchPurchases()
            }
        }
    }

    fun shouldHideAd() = (userPlan.value != null || Session.user.isAdmin())

    private fun fetchPlans() {
        viewModelScope.launch {
            _plans.emit(State.Loading())
            val plans = fetchPlansUseCase(client)
            _plans.emit(State.Success(plans))
        }
    }

    private fun fetchPurchases() {
        client.queryPurchasesAsync(BillingClient.SkuType.SUBS) { billingResult, purchases ->
            handlePurchases(billingResult, purchases)
        }
    }

    private fun handlePurchases(billingResult: BillingResult, purchases: Collection<Purchase>) {
        viewModelScope.launch {
            handlePurchaseUseCase(client, billingResult, purchases) { plan ->
                plan?.let {
                    _userPlan.tryEmit(it)
                }
            }
        }
    }

    fun contract(skuSelected: SkuDetails, launch: (BillingClient, BillingFlowParams) -> Unit) {
        viewModelScope.launch {
            val params = contractPlanUseCase(client, skuSelected, userPlan.value)
            launch(client, params)
        }
    }

    fun getBillingPlan(sku: String?) = repository.getPlanByProductId(sku.orEmpty()).asLiveData()

    override fun onCleared() {
        client.endConnection()
        super.onCleared()
    }
}
