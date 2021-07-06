package br.com.joaoov.data.remote.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String? = null,
    val username: String,
    val password: String,
    val name: String,
    val companyId: String? = null,
    val enabled: Boolean = true,
    val role: String = "",
    val reportCount: Int? = null
) : Parcelable

fun User.toNetwork() = UserNetwork(
    username = username,
    password = password,
    name = name
)
