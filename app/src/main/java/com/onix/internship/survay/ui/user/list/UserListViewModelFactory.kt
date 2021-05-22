package com.onix.internship.survay.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.ui.user.list.UserListViewModel

class UserListViewModelFactory(
    private val dataSource: AppDatabase,
    private val uid: Int) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            return UserListViewModel(dataSource, uid) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
