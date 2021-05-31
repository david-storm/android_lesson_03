package com.onix.internship.survay.ui.logout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LogoutViewModelFactory(

) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogoutViewModel::class.java)) {
            return LogoutViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}