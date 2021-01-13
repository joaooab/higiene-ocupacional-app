package br.com.joaoov.data.local.risk

data class ToleranceLocal(
    val NR15: String,
    val ACGIH: String
)

fun ToleranceLocal.toModel() = Tolerance(
    NR15,
    ACGIH
)

fun List<ToleranceLocal>.toModel() = map { it.toModel() }