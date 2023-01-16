package br.com.joaoov.ui.billing

import br.com.joaoov.data.remote.user.UserPlan
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.SkuDetails

interface ContractPlanUseCase {

    suspend operator fun invoke(
        client: BillingClient,
        skuSelected: SkuDetails,
        userPlan: UserPlan?,
    ): BillingFlowParams
}

class ContractPlanUseCaseImpl : ContractPlanUseCase {

    override suspend fun invoke(
        client: BillingClient,
        skuSelected: SkuDetails,
        userPlan: UserPlan?,
    ) = if (userPlan == null || userPlan.token.isBlank()) {
        BillingFlowParams.newBuilder()
            .setSkuDetails(skuSelected)
            .build()
    } else {
        BillingFlowParams.newBuilder()
            .setSubscriptionUpdateParams(
                BillingFlowParams.SubscriptionUpdateParams.newBuilder()
                    .setOldSkuPurchaseToken(userPlan.token)
                    .setReplaceSkusProrationMode(BillingFlowParams.ProrationMode.IMMEDIATE_WITH_TIME_PRORATION)
                    .build()
            )
            .setSkuDetails(skuSelected)
            .build()
    }
}
