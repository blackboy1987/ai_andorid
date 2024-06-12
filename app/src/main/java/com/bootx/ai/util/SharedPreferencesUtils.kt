package com.bootx.ai.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

val Context.dataStore by preferencesDataStore(name = "settings")

class SharedPreferencesUtils(private val context: Context) {
    fun set(key: String,value: String){
        val dataStoreKey = stringPreferencesKey(key)
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[dataStoreKey] = value
            }
        }
    }

    fun get(key: String): String {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = runBlocking {
            context.dataStore.data.first()
        }
        return preferences[dataStoreKey]?:""
    }

    fun remove(key: String) {
        val dataStoreKey = stringPreferencesKey(key)
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences.remove(dataStoreKey)
            }
        }
    }
}