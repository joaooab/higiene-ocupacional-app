package br.com.joaoov.data.local.agent

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Agent(
    val id: String,
    val code: String,
    val name: String
): Parcelable