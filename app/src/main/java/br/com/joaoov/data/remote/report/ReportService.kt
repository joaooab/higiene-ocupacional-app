package br.com.joaoov.data.remote.report

import retrofit2.http.Body
import retrofit2.http.POST

interface ReportService {

    @POST("/report")
    suspend fun send(@Body report: ReportNetwork)

}


