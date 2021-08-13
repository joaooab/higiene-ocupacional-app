package br.com.joaoov.data.remote.user

data class UserNetwork(
    val id: String? = null,
    val username: String,
    val password: String,
    val name: String,
    val document: String? = null,
    val companyId: String? = null,
    val enabled: Boolean? = null,
    val role: String? = null,
    val reportCount: Int? = null,
    val accessKey: String? = null
)

fun UserNetwork.toModel() = User(
    id = id,
    username = username,
    password = password,
    name = name,
    document = document,
    companyId = companyId,
    enabled = enabled ?: false,
    role = role ?: "",
    reportCount = reportCount,
    accessKey = accessKey
)
