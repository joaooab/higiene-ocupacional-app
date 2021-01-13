package br.com.joaoov.data.local.resource

enum class ResourceAgentCategory(val code: String, val value: String) {
    CHEMICAL_RISKS("01", "Químicos"),
    PHYSICAL_RISKS("02", "Físicos"),
    BIOLOGICAL_RISKS("03", "Biológicos"),
    OTHERS("04", "Outros"),
    UNKNOW("", "");

    companion object {
        fun fromFormatedValue(value: String): ResourceAgentCategory {
            val code = value.split("-")[0].trim()
            return values().firstOrNull { it.code == code } ?: UNKNOW
        }

        fun getFormatedValues() = values().mapNotNull {
            if (it == UNKNOW) null
            else it.getFormatedValue()
        }
    }

}

fun ResourceAgentCategory.getFormatedValue() = "${this.code} - ${this.value}"