package br.com.joaoov.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ResourceService {

    @GET("/ambients")
    suspend fun fetchAllAmbientResources(@Query("updatedAt") updatedAt: String?): List<ResourceAmbientNetwork>

    @GET("/risks")
    suspend fun fetchAllRisksResources(@Query("updatedAt") updatedAt: String?): List<ResourceRiskNetwork>

    @GET("/agents")
    suspend fun fetchAllAgentsResources(@Query("updatedAt") updatedAt: String?): List<ResourceAgentNetwork>

}