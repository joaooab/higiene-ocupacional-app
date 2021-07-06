package br.com.joaoov.data.remote.user

data class UserPassword(
    val username: String,
    val newPassword: String,
    val oldPassword: String
)

fun UserPassword.toNewtork() = UserPasswordNetwork(
    username = username,
    newPassword = newPassword,
    oldPassword = oldPassword
)