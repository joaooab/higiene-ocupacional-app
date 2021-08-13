package br.com.joaoov.repository

import br.com.joaoov.data.remote.user.*


interface UserRepository {

    suspend fun create(user: UserCreate): User

    suspend fun updatePassword(user: UserPassword): User

    suspend fun fetchLinkedUsers(): List<String>

    suspend fun linkUser(username: String)

    suspend fun removeLinkedUser(username: String)
}

class UserRepositoryImpl(private val service: UserService) :
    UserRepository {

    override suspend fun create(user: UserCreate) = service.create(user).toModel()

    override suspend fun updatePassword(user: UserPassword) =
        service.updatePassword(user.toNewtork()).toModel()

    override suspend fun fetchLinkedUsers(): List<String> =
        service.fetchLinkedUsers()

    override suspend fun linkUser(username: String) =
        service.linkUser(UserLinkNetwork(username))

    override suspend fun removeLinkedUser(username: String) =
        service.deleteLinkedUser(username)

}