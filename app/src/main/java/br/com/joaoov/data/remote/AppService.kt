package br.com.joaoov.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "http://192.168.0.27:8080"

object AppRemote {

    val service = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    inline fun <reified T> create() = service.create(T::class.java)

}