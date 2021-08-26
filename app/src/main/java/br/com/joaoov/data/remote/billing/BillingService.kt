package br.com.joaoov.data.remote.billing

import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BillingService {

    @POST("/billing")
    suspend fun create(@Body purchase: JsonObject): BillingNetwork

    @GET("/billing")
    suspend fun fetch(): BillingNetwork

    @GET("/billing/plan")
    suspend fun fetchBillingPlans(): List<BillingPlanNetwork>

}