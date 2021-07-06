package br.com.joaoov.data.remote.auth

import br.com.joaoov.data.remote.user.UserToken
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {

    @POST("/auth")
    suspend fun auth(@Body user: AuthNetwork): UserToken

    @POST("/auth/{email}/recovery")
    suspend fun recoveryPassword(@Path("email") email: String)

}