package br.com.joaoov.data.local.risk

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Risk(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val functionId: Long = 0,
    val name: String,
    var updatedAt: String,
    var deleted: Boolean
): Parcelable

fun Risk.toLocal() = RiskLocal(
    id,
    functionId,
    name,
    updatedAt,
    deleted
)

fun List<Risk>.toLocal() = map { it.toLocal() }