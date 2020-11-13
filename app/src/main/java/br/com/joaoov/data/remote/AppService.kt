package br.com.joaoov.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val URL = "http://192.168.0.38:8080"

object AppRemote {

    private fun createClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .build()
    }

    val service = Retrofit.Builder()
        .baseUrl(URL)
        .client(createClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    inline fun <reified T> create() = service.create(T::class.java)

}