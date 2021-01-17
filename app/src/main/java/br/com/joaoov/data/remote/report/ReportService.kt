package br.com.joaoov.data.remote.report

import br.com.joaoov.data.local.report.Report
import retrofit2.http.Body
import retrofit2.http.POST


interface ReportService {

    @POST("/report")
    suspend fun send(@Body report: Report)

}


