package br.com.joaoov.data.remote.sync

import retrofit2.http.GET
import retrofit2.http.Query

interface SyncService {

    @GET("/sync")
    suspend fun syncronize(@Query("updatedAt") updatedAt: String?): SyncronizeNetwork

}