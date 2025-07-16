package com.example.praktikummdp.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

object PreferenceManager{
    private  val Context.dataStrore by preferencesDataStore("user_prefs")

    private  val TOKEN_KEY = stringPreferencesKey("auth_token")

    suspend fun setToken (context: Context,token : String){
        context.dataStrore.edit {
            prefs -> prefs[TOKEN_KEY]= token
        }
    }

    suspend fun getToken (context: Context): String? {
        val prefs = context.dataStrore.data.first()
        return prefs [TOKEN_KEY]
    }
}