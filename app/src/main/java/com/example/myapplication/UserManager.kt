package com.example.myapplication

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class UserManager(context: Context) {

    private val dataStore = context.dataStore

    // keys to store and retrieve data
    companion object {
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
    }

    // Store user data
    // refer to the data store and using edit
    // store values using the keys
    suspend fun storeUser( name: String) {
        dataStore.edit {
            it[USER_NAME_KEY] = name

        }
    }

    // Create a name flow to retrieve name from the preferences
    val userNameFlow: Flow<String> = dataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }
}


