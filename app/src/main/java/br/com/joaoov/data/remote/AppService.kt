package br.com.joaoov.data.remote

import br.com.joaoov.BuildConfig
import br.com.joaoov.Session
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppService {

    private fun createClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val request = attachToken(chain)
                chain.proceed(request)
            }
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .build()
    }

    private fun attachToken(chain: Interceptor.Chain): Request {
        return if (Session.isLoggedIn()) {
            val token = Session.userToken
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "${token?.type} ${token?.token}")
                .build()
        } else {
            chain.request()
        }
    }

    val service: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BACKEND_URL)
        .client(createClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    inline fun <reified T> create(): T = service.create(T::class.java)

}