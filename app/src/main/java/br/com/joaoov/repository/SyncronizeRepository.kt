package br.com.joaoov.repository

import br.com.joaoov.data.local.syncronize.Syncronize
import br.com.joaoov.data.local.syncronize.SyncronizeDAO
import br.com.joaoov.data.local.syncronize.toLocal
import br.com.joaoov.data.local.syncronize.toModel
import br.com.joaoov.data.remote.SyncService
import br.com.joaoov.data.remote.toModel

interface SyncronizeRepository {

    suspend fun syncronize(updatedAt: String?): Syncronize

    suspend fun getLastSyncronized(): Syncronize?

    suspend fun save(syncronize: Syncronize): Long
}

class SyncronizeRepositoryImpl(private val dao: SyncronizeDAO, private val service: SyncService) :
    SyncronizeRepository {

    override suspend fun syncronize(updatedAt: String?): Syncronize = service.syncronize(updatedAt).toModel()

    override suspend fun getLastSyncronized(): Syncronize? = dao.getLast()?.toModel()

    override suspend fun save(syncronize: Syncronize): Long = dao.save(syncronize.toLocal())

}