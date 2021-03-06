package br.com.joaoov.data.local.risk

import android.os.Parcelable
import br.com.joaoov.data.remote.risk.ToleranceNetwork
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tolerance(
    val NR15: String,
    val ACGIH: String
) : Parcelable

fun Tolerance.toLocal() = ToleranceLocal(
    NR15,
    ACGIH
)

fun List<Tolerance>.toLocal() = map { it.toLocal() }

fun Tolerance.toNetwork() = ToleranceNetwork(
    NR15,
    ACGIH
)

fun List<Tolerance>.toNetwork() = map { it.toNetwork() }