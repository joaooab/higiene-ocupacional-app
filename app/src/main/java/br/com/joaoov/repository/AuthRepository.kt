package br.com.joaoov.repository

import br.com.joaoov.data.remote.auth.Auth
import br.com.joaoov.data.remote.auth.AuthService
import br.com.joaoov.data.remote.auth.toNetwork
import br.com.joaoov.data.remote.user.UserToken


interface AuthRepository {

    suspend fun auth(auth: Auth): UserToken

    suspend fun recoveryPassword(email: String)

}

class AuthRepositoryImpl(private val service: AuthService) :
    AuthRepository {

    override suspend fun auth(auth: Auth) = service.auth(auth.toNetwork())

    override suspend fun recoveryPassword(email: String) = service.recoveryPassword(email)

}
