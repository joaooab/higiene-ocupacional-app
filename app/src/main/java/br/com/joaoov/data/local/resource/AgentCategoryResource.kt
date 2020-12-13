package br.com.joaoov.data.local.resource

enum class AgentCategoryResource(val code: String, val description: String) {
    CHEMICAL_RISKS("01", "Químicos"),
    PHYSICAL_RISKS("02", "Físicos"),
    BIOLOGICAL_RISKS("03", "Biológicos"),
    OTHERS("", "Outros"),
    UNKNOWN("", "UNKNOWN");

//    companion object {
//        fun fromCode(code: String) = values().firstOrNull { it.code == code } ?: OTHERS
//
//        fun fromName(name: String): AgentCategoryResource {
//            return AgentCategoryResource.values().firstOrNull { it.toString() == name.toUpperCase() } ?: UNKNOWN
//        }
//    }

}