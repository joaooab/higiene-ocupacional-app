package br.com.joaoov

import android.content.SharedPreferences
import br.com.joaoov.data.local.PREFS_KEY_SESSION
import br.com.joaoov.data.local.getSerializable
import br.com.joaoov.data.local.set
import br.com.joaoov.data.remote.user.User
import br.com.joaoov.data.remote.user.UserToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.KoinComponent
import org.koin.core.inject

object Session : KoinComponent {

    private val sharedPreferences: SharedPreferences by inject()
    lateinit var userToken: UserToken
    val user: User get() = userToken.user

    private val _sessionState = MutableStateFlow(
        if (isLoggedIn()) SessionState.LoggedIn(userToken)
        else SessionState.Empty
    )
    val sessionState: StateFlow<SessionState> = _sessionState

    fun createSession(token: UserToken) {
        this.userToken = token
        _sessionState.value = SessionState.LoggedIn(token)
        sharedPreferences[PREFS_KEY_SESSION] = token
    }

    fun isLoggedIn(): Boolean {
        if (this::userToken.isInitialized) {
            return true
        } else {
            val localToken = sharedPreferences.getSerializable<UserToken>(PREFS_KEY_SESSION)
            if (localToken != null) {
                userToken = localToken
                return true
            }
            return false
        }
    }

    fun isLoggedOut(): Boolean = !isLoggedIn()

    fun logout() {
        _sessionState.value = SessionState.LoggedOut
        sharedPreferences[PREFS_KEY_SESSION] = null
    }

}

sealed class SessionState {
    object Empty : SessionState()
    class LoggedIn(val userToken: UserToken) : SessionState()
    object LoggedOut : SessionState()
}