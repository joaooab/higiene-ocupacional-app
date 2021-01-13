package br.com.joaoov.data.local.risk

data class AgentLocal(
    val code: String,
    val name: String
) {
    override fun toString(): String = "$code $name"
}

fun AgentLocal.toModel() = Agent(
    code,
    name
)

fun List<AgentLocal>.toModel() = map { it.toModel() }