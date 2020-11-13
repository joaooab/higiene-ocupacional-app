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

    private val _onSyncronize = MutableLiveData<SyncState>()
    val onSyncronize: LiveData<SyncState> = _onSyncronize

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
                    _onSyncronize.postValue(SyncState.Running("Sincronizando ambientes..."))
                    resourceRepository.fetchAllAmbientResources(updateAt)
                    _onSyncronize.postValue(SyncState.Running("Sincronizando riscos..."))
                    resourceRepository.fetchAllRisksResources(updateAt)
                    _onSyncronize.postValue(SyncState.Running("Sincronizando agentes..."))
                    resourceRepository.fetchAllAgentsResources(updateAt)
                    _onSyncronize.postValue(SyncState.Completed)
                    syncronizeRepository.save(syncronize)
                }
            }.onFailure {
                _onSyncronize.postValue(SyncState.Failed(it))
            }
        }

        viewModelScope.launch {
            syncronize.await()
        }
    }

}