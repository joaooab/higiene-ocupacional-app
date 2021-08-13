package br.com.joaoov.data.remote.user

data class UserCreate(
    val username: String,
    val name: String,
    val password: String,
    val document: String?,
    val accessKey: String?
)