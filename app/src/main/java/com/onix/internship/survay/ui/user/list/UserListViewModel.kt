package com.onix.internship.survay.ui.user.list

import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.onix.internship.survay.common.Role
import com.onix.internship.survay.common.SingleLiveEvent
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.database.test.Test
import com.onix.internship.survay.database.user.Change
import com.onix.internship.survay.database.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListViewModel(private val database: AppDatabase, uid: Int, testIdSelected: Int) :
    ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _navigate = SingleLiveEvent<NavDirections>()
    val navigate: LiveData<NavDirections> = _navigate

    lateinit var currentUser: User
    lateinit var selectedTest: Test

    init {
        viewModelScope.launch(Dispatchers.IO) {

            currentUser = database.userDatabaseDao.get(uid).first()
            if (testIdSelected != 0) {
                selectedTest = database.testDatabaseDao.getTest(testIdSelected).first()
            }

            val currentUsers = database.userDatabaseDao.getAllUsers()

            currentUsers.map { user ->
                user.setOnChangeListener(object : Change {
                    override fun onTextChange(value: User) {
                        saveUser(value)
                    }
                })
            }

            _users.postValue(currentUsers)
        }

    }

    fun saveUser(user: User) {

        viewModelScope.launch(Dispatchers.IO) {
            database.userDatabaseDao.update(user)
        }
    }

    fun onUserClicked(uid: Int) {

        when (currentUser.getRoleEnum()) {
            Role.MANAGER -> _navigate.value =
                UserListFragmentDirections.actionUserListFragmentToTestListFragment(
                    currentUser.getUid(), uid
                )
            else -> _navigate.value =
                UserListFragmentDirections.actionUserListFragmentToTestListFragment(
                    currentUser.getUid(), uid
                )
        }
    }
}
