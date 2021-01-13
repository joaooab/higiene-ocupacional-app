package br.com.joaoov.data.local.risk

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Agent(
    val code: String,
    val name: String
) : Parcelable {
    override fun toString(): String = "$code $name"
}

fun Agent.toLocal() = AgentLocal(
    code,
    name
)

fun List<Agent>.toLocal() = map { it.toLocal() }