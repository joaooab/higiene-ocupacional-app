package br.com.joaoov

import android.content.SharedPreferences
import br.com.joaoov.data.local.PREFS_KEY_SESSION
import br.com.joaoov.data.local.getSerializable
import br.com.joaoov.data.local.set
import br.com.joaoov.data.remote.user.User
import br.com.joaoov.data.remote.user.UserToken
import org.koin.core.KoinComponent
import org.koin.core.inject

object Session : KoinComponent {

    private val sharedPreferences: SharedPreferences by inject()
    var userToken: UserToken? = null
    val user: User? get() = userToken?.user

    fun createSession(token: UserToken) {
        this.userToken = token
        sharedPreferences[PREFS_KEY_SESSION] = token
    }

    fun isLoggedIn(): Boolean {
        if (userToken != null) {
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
        userToken = null
        sharedPreferences[PREFS_KEY_SESSION] = null
    }

}

sealed class SessionState {
    object Empty : SessionState()
    class LoggedIn(val userToken: UserToken) : SessionState()
    object LoggedOut : SessionState()
}