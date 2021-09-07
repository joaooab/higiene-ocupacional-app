package br.com.joaoov.data.remote.billing

import com.google.gson.JsonObject
import retrofit2.http.*

interface BillingService {

    @POST("/billing")
    suspend fun create(@Body purchase: JsonObject): BillingNetwork

    @PUT("/billing")
    suspend fun update(@Body purchase: JsonObject): BillingNetwork

    @DELETE("/billing")
    suspend fun delete(): BillingNetwork

    @GET("/billing")
    suspend fun fetch(): BillingNetwork

    @GET("/billing/plan")
    suspend fun fetchBillingPlans(): List<BillingPlanNetwork>

}