package br.com.joaoov.repository

import br.com.joaoov.data.local.syncronize.Syncronize
import br.com.joaoov.data.local.syncronize.SyncronizeDAO
import br.com.joaoov.data.local.syncronize.toLocal
import br.com.joaoov.data.local.syncronize.toModel
import br.com.joaoov.data.remote.sync.SyncService
import br.com.joaoov.data.remote.sync.toModel

interface SyncronizeRepository {

    suspend fun syncronize(updatedAt: String?): Syncronize

    suspend fun getLastSyncronized(): Syncronize?

    suspend fun save(syncronize: Syncronize)

    suspend fun clear()
}

class SyncronizeRepositoryImpl(private val dao: SyncronizeDAO, private val service: SyncService) :
    SyncronizeRepository {

    override suspend fun syncronize(updatedAt: String?): Syncronize = service.syncronize(updatedAt).toModel()

    override suspend fun getLastSyncronized(): Syncronize? = dao.getLast()?.toModel()

    override suspend fun save(syncronize: Syncronize) = dao.save(syncronize.toLocal())

    override suspend fun clear() = dao.clear()

}