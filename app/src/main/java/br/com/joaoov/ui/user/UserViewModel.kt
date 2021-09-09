package br.com.joaoov.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.data.State
import br.com.joaoov.data.remote.user.User
import br.com.joaoov.data.remote.user.UserCreate
import br.com.joaoov.data.remote.user.UserPassword
import br.com.joaoov.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _stateUser = MutableLiveData<State<User>>()
    val stateUser: LiveData<State<User>> = _stateUser

    fun create(user: UserCreate) {
        viewModelScope.launch {
            runCatching {
                _stateUser.value = State.Loading()
                repository.create(user)
            }.onSuccess {
                _stateUser.value = State.Success(it)
            }.onFailure {
                _stateUser.value = State.Error(it)
            }
        }
    }

    fun updatePassword(user: UserPassword) {
        viewModelScope.launch {
            runCatching {
                _stateUser.value = State.Loading()
                repository.updatePassword(user)
            }.onSuccess {
                _stateUser.value = State.Success(it)
            }.onFailure {
                _stateUser.value = State.Error(it)
            }
        }
    }

}