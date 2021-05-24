package com.onix.internship.survay.ui.test.list

import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.onix.internship.survay.common.Role
import com.onix.internship.survay.common.SingleLiveEvent
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.database.test.Test
import com.onix.internship.survay.database.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestListViewModel(private val database: AppDatabase, uid: Int) : ViewModel() {

    private val _tests = MutableLiveData<List<Test>>()
    val tests: LiveData<List<Test>> = _tests

    private val _navigate = SingleLiveEvent<NavDirections>()
    val navigate: LiveData<NavDirections> = _navigate

    lateinit var currentUser: User

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currentUser = database.userDatabaseDao.get(uid).first()
            _tests.postValue(database.testDatabaseDao.testAll())
        }
    }


    fun onTestClicked(id: Int) {
        when (currentUser.getRoleEnum()) {
            Role.USER -> _navigate.value =
                TestListFragmentDirections.actionTestListFragmentToTestRunFragment(id)
            Role.ADMIN ->
                viewModelScope.launch(Dispatchers.IO) {
                    _tests.postValue(database.testDatabaseDao.testAll())
                }
            else -> throw ClassCastException("Error role")
        }
    }

}