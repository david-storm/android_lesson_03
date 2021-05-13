package com.onix.internship.survay.auth.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onix.internship.survay.database.user.UserDatabaseDao

class RegisterViewModelFactory (

    private val dataSource: UserDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory
    {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                return RegisterViewModel(dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

