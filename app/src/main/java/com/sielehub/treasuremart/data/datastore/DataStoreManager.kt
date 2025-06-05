package com.sielehub.treasuremart.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val dataStore: DataStore<Preferences>) {
    companion object {
        val ON_BOARDING_DONE_KEY = booleanPreferencesKey("on_boarding_done")
        val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        val SEARCH_HISTORY_KEY = stringPreferencesKey("search_history")
        val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
        val CURRENT_USER_ID_KEY = intPreferencesKey("current_user_id")
    }

    val onBoardingDone: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[ON_BOARDING_DONE_KEY] == true
    }
    val authToken: Flow<String?> = dataStore.data.map { prefs ->
        prefs[AUTH_TOKEN_KEY]
    }
    val currentUserId: Flow<Int> = dataStore.data.map { prefs ->
        prefs[CURRENT_USER_ID_KEY] ?: -1
    }
    val searchHistory: Flow<String> = dataStore.data.map { prefs ->
        prefs[SEARCH_HISTORY_KEY] ?: "[]"
    }
    val themeMode: Flow<String> = dataStore.data.map { prefs ->
        prefs[THEME_MODE_KEY] ?: "System default"
    }

    suspend fun setOnBoardingDone(onBoardingDone: Boolean) {
        dataStore.edit { prefs ->
            prefs[ON_BOARDING_DONE_KEY] = onBoardingDone
        }
    }

    suspend fun setAuthToken(authToken: String) {
        dataStore.edit { prefs ->
            prefs[AUTH_TOKEN_KEY] = authToken
        }
    }

    suspend fun setCurrentUserId(userId: Int) {
        dataStore.edit { prefs ->
            prefs[CURRENT_USER_ID_KEY] = userId
        }
    }

    suspend fun setSearchHistory(searchHistory: String) {
        dataStore.edit { prefs ->
            prefs[SEARCH_HISTORY_KEY] = searchHistory
        }
    }

    suspend fun setAppTheme(selectedMode: String) {
        dataStore.edit { prefs ->
            prefs[THEME_MODE_KEY] = selectedMode
        }
    }
}
