package br.com.joaoov.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.State
import br.com.joaoov.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccessKeyViewModel(private val repository: UserRepository) : ViewModel() {

    private val _linkedUsers = MutableLiveData<State<List<String>>>()
    val linkedUsers: LiveData<State<List<String>>> = _linkedUsers

    private val _linkUser = MutableLiveData<State<Unit>>()
    val linkUser: LiveData<State<Unit>> = _linkUser

    private val _removeLinkedUser = MutableLiveData<State<Unit>>()
    val removeLinkedUser: LiveData<State<Unit>> = _removeLinkedUser

    fun fetchLinkedUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _linkedUsers.postValue(State.Loading())
                repository.fetchLinkedUsers()
            }.onSuccess {
                _linkedUsers.postValue(State.Success(it))
            }.onFailure {
                _linkedUsers.postValue(State.Error(it))
            }
        }
    }

    fun linkUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _linkUser.postValue(State.Loading())
                repository.linkUser(username)
            }.onSuccess {
                _linkUser.postValue(State.Success(it))
            }.onFailure {
                _linkUser.postValue(State.Error(it))
            }
        }
    }

    fun deleteLinkedUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _removeLinkedUser.postValue(State.Loading())
                repository.removeLinkedUser(username)
            }.onSuccess {
                _removeLinkedUser.postValue(State.Success(it))
            }.onFailure {
                _removeLinkedUser.postValue(State.Error(it))
            }
        }
    }

}