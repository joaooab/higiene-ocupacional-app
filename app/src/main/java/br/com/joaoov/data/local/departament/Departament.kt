package br.com.joaoov.data.local.departament

import android.os.Parcelable
import br.com.joaoov.data.remote.departament.DepartamentNetwork
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Departament(
    val id: Long = 0,
    val companyId: Long = 0,
    val name: String = "",
    val date: String = ""
) : Parcelable {

    override fun toString() = name

}

fun Departament.toLocal() =
    DepartamentLocal(
        id,
        companyId,
        name,
        date
    )

fun List<Departament>.toLocal() = map { it.toLocal() }

fun Departament.toNetwork() =
    DepartamentNetwork(
        id,
        companyId,
        name,
        date
    )

fun List<Departament>.toNetwork() = map { it.toNetwork() }