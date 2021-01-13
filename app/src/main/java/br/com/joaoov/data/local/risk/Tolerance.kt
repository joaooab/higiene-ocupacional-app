package br.com.joaoov.data.local.risk

import android.os.Parcelable
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