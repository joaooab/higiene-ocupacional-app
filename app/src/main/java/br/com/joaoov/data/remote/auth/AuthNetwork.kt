package br.com.joaoov.data.remote.auth

data class AuthNetwork(
    val username: String,
    val password: String
)

fun AuthNetwork.toModel() = Auth(
    username = username,
    password = password
)