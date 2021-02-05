package br.com.joaoov.data.local.function

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.joaoov.data.local.ambient.AmbientLocal

@Entity(
    tableName = "function",
    foreignKeys = [
        ForeignKey(
            entity = AmbientLocal::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("ambientId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ambientId")]
)
data class FunctionLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val ambientId: Long = 0,
    val name: String,
    val date: String,
    val description: String,
    val workday: String
)

fun FunctionLocal.toModel() =
    Function(
        id,
        ambientId,
        name,
        date,
        description,
        workday
    )

fun List<FunctionLocal>.toModel() = map { it.toModel() }