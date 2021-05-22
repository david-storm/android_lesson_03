package com.onix.internship.survay.ui.test.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onix.internship.survay.database.AppDatabase

class TestListViewModelFactory(
    private val dataSource: AppDatabase,
    private val uid: Int) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestListViewModel::class.java)) {
            return TestListViewModel(dataSource, uid) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
