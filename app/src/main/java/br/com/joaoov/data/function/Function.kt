package br.com.joaoov.data.function

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Function(
    val id: Long = 0,
    val ambientId: Long = 0,
    val name: String,
    val date: String,
    val description: String,
    val workday: String
) : Parcelable {

    @IgnoredOnParcel
    var showDetail: Boolean = false
}

fun Function.toLocal() =
    FunctionLocal(
        id,
        ambientId,
        name,
        date,
        description,
        workday
    )

fun List<Function>.toLocal() = map { it.toLocal() }