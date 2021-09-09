package br.com.joaoov.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaoov.Session
import br.com.joaoov.data.State
import br.com.joaoov.data.remote.auth.Auth
import br.com.joaoov.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _stateLogin = MutableLiveData<State<Unit>>()
    val stateAuth: LiveData<State<Unit>> = _stateLogin

    private val _stateRecoveryPassword = MutableLiveData<State<Unit>>()
    val stateRecoveryPassword: LiveData<State<Unit>> = _stateRecoveryPassword

    fun auth(auth: Auth) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _stateLogin.postValue(State.Loading())
                repository.auth(auth)
            }.onSuccess {
                _stateLogin.postValue(State.Success(Unit))
                Session.createSession(it)
            }.onFailure {
                _stateLogin.postValue(State.Error(it))
            }
        }
    }

    fun recoveryPassword(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _stateRecoveryPassword.postValue(State.Loading())
                repository.recoveryPassword(email)
            }.onSuccess {
                _stateRecoveryPassword.postValue(State.Success(Unit))
            }.onFailure {
                _stateRecoveryPassword.postValue(State.Error(it))
            }
        }
    }

}