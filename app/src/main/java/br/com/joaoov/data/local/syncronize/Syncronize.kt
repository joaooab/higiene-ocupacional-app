package br.com.joaoov.data.local.syncronize

data class Syncronize(
    val createdAt: String,
    val shouldRun: Boolean
)

fun Syncronize.toLocal() = SyncronizeLocal(
    createdAt = createdAt,
    shouldRun = shouldRun
)