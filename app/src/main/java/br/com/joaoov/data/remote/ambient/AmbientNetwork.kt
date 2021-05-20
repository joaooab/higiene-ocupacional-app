package br.com.joaoov.data.remote.ambient

data class AmbientNetwork(
    val id: Long?,
    val departamentId: Long?,
    val date: String?,
    val name: String?,
    val area: String?,
    val height: String?,
    val floor: String?,
    val wall: String?,
    val roof: String?,
    val roofTiles: String?,
    val window: String?,
    val ceiling: String?,
    val naturalLighting: String?,
    val artificialLighting: String?,
    val naturalVentilation: String?,
    val artificialVentilation: String?,
    val structure: String?
)