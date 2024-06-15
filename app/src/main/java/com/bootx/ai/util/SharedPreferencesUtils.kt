package com.bootx.ai.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
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



    fun getToken(): String {
        val dataStoreKey = stringPreferencesKey("token")
        val preferences = runBlocking {
            context.dataStore.data.first()
        }
        return preferences[dataStoreKey]?:""
    }

    fun setToken(token:String) {
        val dataStoreKey = stringPreferencesKey("token")
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[dataStoreKey] = token
            }
        }
    }

    fun getDeviceId(): String {
        val dataStoreKey = stringPreferencesKey("deviceId")
        val preferences = runBlocking {
            context.dataStore.data.first()
        }
        return preferences[dataStoreKey]?:""
    }

    fun setDeviceId(deviceId:String) {
        val dataStoreKey = stringPreferencesKey("deviceId")
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[dataStoreKey] = deviceId
            }
        }
    }
    fun getCodeCountDown(): String {
        val dataStoreKey = stringPreferencesKey("codeCountDown")
        val preferences = runBlocking {
            context.dataStore.data.first()
        }
        return preferences[dataStoreKey]?:"0"
    }

    fun setCodeCountDown(codeCodeDown:String) {
        val dataStoreKey = stringPreferencesKey("codeCountDown")
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[dataStoreKey] = codeCodeDown
            }
        }
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