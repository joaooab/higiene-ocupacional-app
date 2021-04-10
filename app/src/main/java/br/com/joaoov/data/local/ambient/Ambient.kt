package br.com.joaoov.data.local.ambient

import android.os.Parcelable
import br.com.joaoov.data.remote.ambient.AmbientNetwork
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ambient(
    val id: Long = 0,
    val departamentId: Long = 0,
    val date: String = "",
    val name: String = "",
    val area: String = "",
    val height: String = "",
    val floor: String = "",
    val wall: String = "",
    val roof: String = "",
    val roofTiles: String = "",
    val window: String = "",
    val ceiling: String = "",
    val naturalLighting: String = "",
    val artificialLighting: String = "",
    val naturalVentilation: String = "",
    val artificialVentilation: String = ""
) : Parcelable {

    @IgnoredOnParcel
    var showDetail: Boolean = false

    override fun toString(): String {
        return name
    }

}

fun Ambient.toLocal() =
    AmbientLocal(
        id,
        departamentId,
        date,
        name,
        area,
        height,
        floor,
        wall,
        roof,
        roofTiles,
        window,
        ceiling,
        naturalLighting,
        artificialLighting,
        naturalVentilation,
        artificialVentilation
    )

fun List<Ambient>.toLocal() = map { it.toLocal() }

fun Ambient.toNetwork() =
    AmbientNetwork(
        id,
        departamentId,
        date,
        name,
        area,
        height,
        floor,
        wall,
        roof,
        roofTiles,
        window,
        ceiling,
        naturalLighting,
        artificialLighting,
        naturalVentilation,
        artificialVentilation
    )

fun List<Ambient>.toNetwork() = map { it.toNetwork() }