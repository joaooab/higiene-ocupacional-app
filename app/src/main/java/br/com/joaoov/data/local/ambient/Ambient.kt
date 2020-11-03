package br.com.joaoov.data.local.ambient

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ambient(
    val id: Long = 0,
    val departamentId: Long,
    val date: String,
    val name: String,
    val width: Double,
    val length: Double,
    val height: Double,
    val floor: String,
    val wall: String,
    val coverage: String,
    val naturalLighting: String,
    val artificialLighting: String,
    val naturalVentilation: String,
    val artificialVentilation: String
) : Parcelable {
    fun getAreaFormat() = "${width}m x ${length}m"

    fun getHeightFormat() = "${height}m"

    @IgnoredOnParcel
    var showDetail: Boolean = false
}

fun Ambient.toLocal() =
    AmbientLocal(
        id,
        departamentId,
        date,
        name,
        width,
        length,
        height,
        floor,
        wall,
        coverage,
        naturalLighting,
        artificialLighting,
        naturalVentilation,
        artificialVentilation
    )

fun List<Ambient>.toLocal() = map { it.toLocal() }