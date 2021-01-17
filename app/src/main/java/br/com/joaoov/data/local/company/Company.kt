package br.com.joaoov.data.local.company

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    val id: Long = 0,
    val name: String,
    val date: String
) : Parcelable

fun Company.toLocal() =
    CompanyLocal(
        id,
        name,
        date
    )

fun List<Company>.toLocal() = map { it.toLocal() }