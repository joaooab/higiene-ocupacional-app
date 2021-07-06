package br.com.joaoov.data.remote.user

data class UserPasswordNetwork(
    val username: String,
    val newPassword: String,
    val oldPassword: String
)