package br.com.joaoov.data.remote.sync

import br.com.joaoov.data.local.syncronize.Syncronize

data class SyncronizeNetwork(
    val _id: String?,
    val createdAt: String?,
    val shouldRun: Boolean?
)

fun SyncronizeNetwork.toModel() = Syncronize(
    createdAt.orEmpty(),
    shouldRun ?: false
)