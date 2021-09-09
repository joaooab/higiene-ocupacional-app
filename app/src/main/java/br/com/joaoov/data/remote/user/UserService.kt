package br.com.joaoov.data.remote.user

import retrofit2.http.*

interface UserService {

    @POST("/user")
    suspend fun create(@Body user: UserCreate): UserNetwork

    @PUT("/user/password")
    suspend fun updatePassword(@Body user: UserPasswordNetwork): UserNetwork

    @GET("/user/accesskey")
    suspend fun fetchLinkedUsers(): List<String>

    @POST("/user/accesskey")
    suspend fun linkUser(@Body userLink: UserLinkNetwork)

    @DELETE("/user/accesskey/{username}")
    suspend fun deleteLinkedUser(@Path("username") username: String)

}