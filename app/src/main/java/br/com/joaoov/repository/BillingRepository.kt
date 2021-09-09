package br.com.joaoov.repository

import br.com.joaoov.data.local.billing.*
import br.com.joaoov.data.remote.billing.BillingService
import br.com.joaoov.data.remote.billing.toModel
import com.android.billingclient.api.Purchase
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


interface BillingRepository {

    suspend fun create(purchase: Purchase): Billing

    suspend fun update(purchase: Purchase): Billing

    suspend fun delete(): Billing

    suspend fun fetchPlans()

    fun getPlanByProductId(productId: String): Flow<BillingPlan?>

    fun getPlans(): Flow<List<BillingPlan>>

    fun fetch(): Flow<Billing>

    suspend fun clearPlans()
}


class BillingRepositoryImpl(
    private val service: BillingService,
    private val dao: BillingPlanDAO
) : BillingRepository {

    override suspend fun create(purchase: Purchase): Billing {
        val json = Gson().fromJson(purchase.originalJson, JsonObject::class.java)
        return service.create(json).toModel()
    }

    override suspend fun update(purchase: Purchase): Billing {
        val json = Gson().fromJson(purchase.originalJson, JsonObject::class.java)
        return service.create(json).toModel()
    }

    override suspend fun delete(): Billing {
        return service.delete().toModel()
    }

    override suspend fun fetchPlans() {
        val plans = service.fetchBillingPlans().toModel()
        dao.save(plans.toLocal())
    }

    override fun getPlanByProductId(productId: String): Flow<BillingPlan?> =
        dao.getByProductId(productId).map { it?.toModel() }

    override fun getPlans(): Flow<List<BillingPlan>> =
        dao.getAll().map { plans -> plans.map { it.toModel() } }

    override fun fetch(): Flow<Billing> = flow { emit(service.fetch().toModel()) }

    override suspend fun clearPlans() = dao.clear()

}
