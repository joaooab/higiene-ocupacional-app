package br.com.joaoov.data.local.company

import android.os.Parcelable
import br.com.joaoov.data.remote.company.CompanyNetwork
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    val id: Long = 0,
    val userId: String = "",
    val name: String = "",
    val date: String = ""
) : Parcelable {

    override fun toString() = name

}

fun Company.toLocal() =
    CompanyLocal(
        id,
        userId,
        name,
        date
    )

fun List<Company>.toLocal() = map { it.toLocal() }

fun Company.toNetwork() =
    CompanyNetwork(
        id,
        userId,
        name,
        date
    )

fun List<Company>.toNetwork() = map { it.toNetwork() }