package br.com.joaoov.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Ambient",
    foreignKeys = [
        ForeignKey(
            entity = DepartamentLocal::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("departamentId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AmbientLocal(
    @PrimaryKey(autoGenerate = true)
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
)

fun AmbientLocal.toModel() =
    Ambient(
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

fun List<AmbientLocal>.toModel() = map { it.toModel() }