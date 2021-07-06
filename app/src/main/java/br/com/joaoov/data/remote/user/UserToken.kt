package br.com.joaoov.data.remote.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserToken(
    val token: String,
    val type: String,
    val user: User
): Parcelable