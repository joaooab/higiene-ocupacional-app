package br.com.joaoov.data.local.resource

enum class ResourceRiskCategory(val value: String) {
    GENERATED_SOURCE("Fonte geradora"),
    EXPOSURE("Modo de exposição"),
    ELIMINATION_NEUTRALIZATION("ELIMINAÇÃO E/OU NEUTRALIZAÇÃO"),
    METHODOLOGY("Fonte/Metodologia"),
    TRAJECTORY("Trajetória"),
    DEGREE_OF_RISK("Grau de risco"),
    UNKNOWN("");

    companion object {
        fun fromValue(value: String) = values().firstOrNull { it.value == value } ?: UNKNOWN

        fun fromName(name: String): ResourceRiskCategory {
            return values().firstOrNull { it.toString() == name.toUpperCase() } ?: UNKNOWN
        }
    }

}