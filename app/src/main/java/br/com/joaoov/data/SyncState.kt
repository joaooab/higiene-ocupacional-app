package br.com.joaoov.data

sealed class SyncState {
    class Running(val message: String) : SyncState()
    object Completed : SyncState()
    class Failed(val throwable: Throwable) : SyncState()
}