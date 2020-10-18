package br.com.joaoov.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ambient(
    val id: Long = 0,
    val departamentId: Long,
    val data: String,
    val local: String,
    val areaLargura: Double,
    val areaComprimento: Double,
    val peDireito: Double,
    val piso: String,
    val parede: String,
    val cobertura: String,
    val iluminacaoNatural: String,
    val iluminacaoArtificial: String,
    val ventilacaoNatural: String,
    val ventilacaoArtificial: String
) : Parcelable {
    fun getAreaFormat() = "${areaLargura}m x ${areaComprimento}m"

    fun getPeDireitoFormat() = "${peDireito}m"
}

fun Ambient.toLocal() =
    AmbientLocal(
        id,
        departamentId,
        data,
        local,
        areaLargura,
        areaComprimento,
        peDireito,
        piso,
        parede,
        cobertura,
        iluminacaoNatural,
        iluminacaoArtificial,
        ventilacaoNatural,
        ventilacaoArtificial
    )

fun List<Ambient>.toLocal() = map { it.toLocal() }