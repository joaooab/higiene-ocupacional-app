package br.com.joaoov.data.local

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import com.google.gson.Gson
import java.io.Serializable

const val PREFS_KEY_SESSION = "KEY_SESSION"

private const val FILE_NAME = "br.com.joaoovf.prefs"

fun defaultPref(context: Context): SharedPreferences =
    context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}

operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        is Serializable?, is Parcelable? -> edit { it.putString(key, Gson().toJson(value)) }
        is Collection<*> -> edit {
            it.putStringSet(
                key,
                value.map { v -> v.toString() }.toSet()
            )
        }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}


inline operator fun <reified T : Any> SharedPreferences.get(
    key: String,
    defaultValue: T
): T? {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as? T
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        Set::class -> getStringSet(key, emptySet()) as T?
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

inline fun <reified T : Any> SharedPreferences.getSerializable(
    key: String
): T? {
    return try {
        Gson().fromJson(getString(key, ""), T::class.java)
    } catch (e: Exception) {
        null
    }
}