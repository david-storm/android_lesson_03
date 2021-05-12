package com.onix.internship.survay.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.onix.internship.survay.database.user.User
import com.onix.internship.survay.database.user.UserDatabaseDao
import kotlinx.coroutines.launch

class ListViewModel(private val database: UserDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val _users = MutableLiveData<List<User>?>()
    val users: LiveData<List<User>?>
        get() = _users

    init {
        viewModelScope.launch {
            _users.value = database.getAllUsers()
        }
    }
}