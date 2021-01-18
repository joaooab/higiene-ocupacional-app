package br.com.joaoov

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.SyncState
import br.com.joaoov.repository.ResourceRepository
import br.com.joaoov.repository.SyncronizeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SyncViewModel(
    private val syncronizeRepository: SyncronizeRepository,
    private val resourceRepository: ResourceRepository
) : ViewModel() {

    private val _syncronizeState = MutableLiveData<SyncState>()
    val syncronizeState: LiveData<SyncState> = _syncronizeState

    init {
        syncronize()
    }

    private fun syncronize() {
        val syncronize = viewModelScope.async {
            runCatching {
                val lastSyncronized = syncronizeRepository.getLastSyncronized()
                val syncronize = syncronizeRepository.syncronize(lastSyncronized?.createdAt)
                if (syncronize.shouldRun) {
                    val updateAt = lastSyncronized?.createdAt
                    _syncronizeState.postValue(SyncState.Running("Sincronizando ambientes..."))
                    resourceRepository.fetchAllAmbientResources(updateAt)
                    _syncronizeState.postValue(SyncState.Running("Sincronizando riscos..."))
                    resourceRepository.fetchAllRisksResources(updateAt)
                    _syncronizeState.postValue(SyncState.Running("Sincronizando agentes..."))
                    resourceRepository.fetchAllAgentsResources(updateAt)
                    _syncronizeState.postValue(SyncState.Completed)
                    syncronizeRepository.save(syncronize)
                }
            }.onFailure {
                _syncronizeState.postValue(SyncState.Failed(it))
            }
        }

        viewModelScope.launch {
            syncronize.await()
        }
    }

    fun forceSyncronize() {
        viewModelScope.launch {
            syncronizeRepository.clear()
            syncronize()
        }
    }

}