/*
package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userManager = UserManager(application)

    fun storeUser(name: String) {
        viewModelScope.launch {
            userManager.storeUser(name)
        }
    }

    val userNameFlow = userManager.userNameFlow
}
 */