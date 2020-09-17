package br.com.joaoov.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Ambient(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val empresa: String,
    val data: String,
    val local: String,
    val areaLargura: Double,
    val areaComprimento: Double,
    val peDireitoLargura: Double,
    val peDireitoComprimento: Double,
    val piso: String,
    val parede: String,
    val cobertura: String,
    val iluminacaoNatural: String,
    val iluminacaoArtificial: String,
    val ventilacaoNatural: String,
    val ventilacaoArtificial: String
) : Parcelable {
    fun getArea() = "${areaLargura}m x ${areaComprimento}m"

    fun getPeDireito() = "${peDireitoLargura}m x ${peDireitoComprimento}m"
}
