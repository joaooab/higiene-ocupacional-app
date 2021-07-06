package br.com.joaoov.data.remote.user

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {

    @POST("/user")
    suspend fun create(@Body user: UserNetwork): UserNetwork

    @PUT("/user/password")
    suspend fun updatePassword(@Body user: UserPasswordNetwork): UserNetwork

}