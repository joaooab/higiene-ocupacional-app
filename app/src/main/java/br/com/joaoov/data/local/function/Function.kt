package br.com.joaoov.data.local.function

import android.os.Parcelable
import br.com.joaoov.data.remote.function.FunctionNetwork
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Function(
    val id: Long = 0,
    val ambientId: Long = 0,
    val name: String,
    val date: String,
    val description: String,
    val amount: Int?,
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
        amount,
        workday
    )

fun List<Function>.toLocal() = map { it.toLocal() }


fun Function.toNetwork() =
    FunctionNetwork(
        id,
        ambientId,
        name,
        date,
        description,
        amount,
        workday
    )

fun List<Function>.toNetwork() = map { it.toNetwork() }