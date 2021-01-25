package br.com.joaoov.data.local.resource

enum class ResourceAgentCategory(val code: String, val value: String) {
    CHEMICAL_RISKS("01", "Químicos (Previdência)"),
    PHYSICAL_RISKS("02", "Físicos (Previdência)"),
    BIOLOGICAL_RISKS("03", "Biológicos (Previdência)"),
    OTHERS("04", "Outros (Previdência)"),
    PHYSICAL_RISKS_OLD("01", "Físicos (Trabalhista)"),
    CHEMICAL_RISKS_OLD("02", "Químicos (Trabalhista)"),
    BIOLOGICAL_RISKS_OLD("03", "Biológicos (Trabalhista)"),
    ERGONOMIC_RISKS_OLD("04", "Ergonômicos (Trabalhista)"),
    MECHANICAL_RISKS_OLD("05", "Mecânicos (Trabalhista)"),
    OTHERS_OLD("06", "Outros (Trabalhista)"),
    UNKNOWN("", "UNKNOWN");

    companion object {
        fun fromFormatedValue(value: String): ResourceAgentCategory {
            return values().firstOrNull { it.getFormatedValue() == value } ?: UNKNOWN
        }

        fun getFormatedValues(isESocial: Boolean = true) = values().mapNotNull {
            if (it == UNKNOWN) {
                null
            } else if (isESocial && it.isESocial()) {
                it.getFormatedValue()
            } else if (!isESocial && it.isOld()) {
                it.getFormatedValue()
            } else {
                null
            }
        }
    }

}

fun ResourceAgentCategory.getFormatedValue() = "${this.code} - ${this.value}"

fun ResourceAgentCategory.isOld() = this.toString().contains("OLD")

fun ResourceAgentCategory.isESocial() = !isOld()