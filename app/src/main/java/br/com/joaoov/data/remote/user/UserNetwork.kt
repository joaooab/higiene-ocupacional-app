package br.com.joaoov.data.remote.user

import br.com.joaoov.data.local.billing.Billing

data class UserNetwork(
    val id: String? = null,
    val username: String,
    val password: String,
    val name: String,
    val billing: Billing? = null,
    val document: String? = null,
    val companyId: String? = null,
    val enabled: Boolean? = null,
    val role: String? = null,
    val accessKey: String? = null
)

fun UserNetwork.toModel() = User(
    id = id,
    username = username,
    password = password,
    name = name,
    document = document,
    productId = billing?.productId ?: Billing.DEFAULT_PRODUCT,
    companyId = companyId,
    enabled = enabled ?: false,
    role = role ?: "",
    accessKey = accessKey
)
