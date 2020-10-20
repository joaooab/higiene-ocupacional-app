package br.com.joaoov.data.departament

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Departament(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val companyId: Long = 0,
    val name: String,
    val date: String
) : Parcelable

fun Departament.toLocal() =
    DepartamentLocal(
        id,
        companyId,
        name,
        date
    )

fun List<Departament>.toLocal() = map { it.toLocal() }