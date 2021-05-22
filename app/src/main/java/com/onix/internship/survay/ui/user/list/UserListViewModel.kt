package com.onix.internship.survay.ui.user.list

import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.onix.internship.survay.common.Role
import com.onix.internship.survay.common.SingleLiveEvent
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.database.user.User
import com.onix.internship.survay.ui.list.UserListFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListViewModel(private val database: AppDatabase, uid: Int) : ViewModel() {

    private val _data = MutableLiveData<List<Any>>()
    val data: LiveData<List<Any>> = _data

    private val _navigate = SingleLiveEvent<NavDirections>()
    val navigate: LiveData<NavDirections> = _navigate

    lateinit var currentUser: User

    init {


        viewModelScope.launch(Dispatchers.IO) {

            currentUser = database.userDatabaseDao.get(uid).first()

            when (currentUser.getRoleEnum()) {
                Role.MANAGER -> _data.postValue(database.userDatabaseDao.getAllUsers())
                else -> _data.postValue(database.testDatabaseDao.testAll())
            }
        }
    }

    fun onUserClicked(uid: Int) {
        _navigate.value = UserListFragmentDirections.actionListFragmentToUserSettingFragment(uid)
    }

    fun onTestClicked(id: Int) {
        when (currentUser.getRoleEnum()) {
            Role.USER -> _navigate.value =
                UserListFragmentDirections.actionListFragmentToTestRunFragment(id)
            Role.ADMIN ->
                viewModelScope.launch(Dispatchers.IO) {
                    _data.postValue(database.userDatabaseDao.getAllUsers())
                }
            else -> throw ClassCastException("Error role")
        }
    }

}