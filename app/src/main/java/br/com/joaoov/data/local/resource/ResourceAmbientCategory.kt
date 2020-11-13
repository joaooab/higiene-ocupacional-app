package br.com.joaoov.data.local.resource

enum class ResourceAmbientCategory(val value: String) {
    WALL("Parede"),
    FLOOR("Piso"),
    ROOF("Cobertura"),
    ROOF_TILES("Telhas"),
    NATURAL_LIGHTING("Iluminação Natural"),
    ARTIFICIAL_LIGHTING("Iluminação Artificial"),
    NATURAL_VENTILATION("Ventilação Natural"),
    ARTIFICIAL_VENTILATION("Ventilação Artificial"),
    WINDOWN("Janela"),
    CEILING("Forro"),
    UNKNOWN("");

    companion object {
        fun fromValue(value: String) = values().firstOrNull { it.value == value } ?: UNKNOWN

        fun fromName(name: String): ResourceAmbientCategory {
            return values().firstOrNull { it.toString() == name.toUpperCase() } ?: UNKNOWN
        }
    }

}