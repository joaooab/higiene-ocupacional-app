package br.com.joaoov.repository

import br.com.joaoov.data.remote.user.*


interface UserRepository {

    suspend fun create(user: User): User
    suspend fun updatePassword(user: UserPassword): User

}

class UserRepositoryImpl(private val service: UserService) :
    UserRepository {

    override suspend fun create(user: User) = service.create(user.toNetwork()).toModel()

    override suspend fun updatePassword(user: UserPassword) =
        service.updatePassword(user.toNewtork()).toModel()

}