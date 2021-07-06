package br.com.joaoov.data.remote.auth

data class Auth(
    val username: String,
    val password: String
)

fun Auth.toNetwork() = AuthNetwork(
    username = username,
    password = password
)