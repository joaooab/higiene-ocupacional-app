package br.com.joaoov.ui.billing

import br.com.joaoov.data.local.billing.BillingPlan
import br.com.joaoov.repository.BillingRepository
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.SkuDetailsParams
import com.android.billingclient.api.SkuDetailsResult
import com.android.billingclient.api.querySkuDetails
import kotlinx.coroutines.flow.first

interface FetchPlansUseCase {

    suspend operator fun invoke(client: BillingClient): SkuDetailsResult
}

class FetchPlansUseCaseImpl(
    private val repository: BillingRepository
) : FetchPlansUseCase {

    override suspend fun invoke(client: BillingClient): SkuDetailsResult {
        val plans = repository.getPlans().first()
        val params = createSkuListParams(plans)
        return client.querySkuDetails(params)
    }

    private fun createSkuListParams(plans: List<BillingPlan>): SkuDetailsParams {
        val skuList = plans.map { it.productId }
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)

        return params.build()
    }
}
