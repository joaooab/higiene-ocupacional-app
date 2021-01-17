package br.com.joaoov.data.local.ambient

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.joaoov.data.local.departament.DepartamentLocal

@Entity(
    tableName = "ambient",
    foreignKeys = [
        ForeignKey(
            entity = DepartamentLocal::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("departamentId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("departamentId")]
)
data class AmbientLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val departamentId: Long,
    val date: String,
    val local: String,
    val width: Double,
    val lenght: Double,
    val height: Double,
    val floor: String,
    val wall: String,
    val roof: String,
    val roofTiles: String,
    val window: String,
    val ceiling: String,
    val naturalLighting: String,
    val artificialLighting: String,
    val naturalVentilation: String,
    val artificialVentilation: String
)

fun AmbientLocal.toModel() =
    Ambient(
        id,
        departamentId,
        date,
        local,
        width,
        lenght,
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

fun List<AmbientLocal>.toModel() = map { it.toModel() }