package code.monkeys.zarqa.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager private constructor(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @SuppressLint("StaticFieldLeak")
    companion object {
        @Volatile
        private var INSTANCE: DataStoreManager? = null

        fun getInstance(context: Context): DataStoreManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataStoreManager(context).also { INSTANCE = it }
            }
        }

        private val TOKEN_KEY = stringPreferencesKey("token")
        private val NAME_KEY = stringPreferencesKey("name")
        private val OUTLET_NAME_KEY = stringPreferencesKey("outlet_name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val ROLE_KEY = stringPreferencesKey("role")
    }

    val emailFlow: Flow<String?> = context.dataStore.data
        .map {
            it[EMAIL_KEY]
        }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
        }
    }

    suspend fun clearEmail() {
        context.dataStore.edit { preferences ->
            preferences.remove(EMAIL_KEY)
        }
    }

    val passwordFlow: Flow<String?> = context.dataStore.data
        .map {
            it[PASSWORD_KEY]
        }

    suspend fun savePassword(password: String) {
        context.dataStore.edit { preferences ->
            preferences[PASSWORD_KEY] = password
        }
    }

    suspend fun clearPassword() {
        context.dataStore.edit { preferences ->
            preferences.remove(PASSWORD_KEY)
        }
    }

    val tokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY]
        }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    val roleFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[ROLE_KEY]
        }

    suspend fun saveRole(role: String) {
        context.dataStore.edit { preferences ->
            preferences[ROLE_KEY] = role
        }
    }

    suspend fun clearRole() {
        context.dataStore.edit { preferences ->
            preferences.remove(ROLE_KEY)
        }
    }

}