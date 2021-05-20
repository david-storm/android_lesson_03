package com.onix.internship.survay.ui.list

import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.onix.internship.survay.common.Role
import com.onix.internship.survay.common.SingleLiveEvent
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.database.test.Test
import com.onix.internship.survay.database.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(private val database: AppDatabase, uid: Int) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

  private val _tests = MutableLiveData<List<Test>>()
    val tests: LiveData<List<Test>> = _tests


    private val _navigateToUser = SingleLiveEvent<NavDirections>()
    val navigateToUser: LiveData<NavDirections> = _navigateToUser

    lateinit var currentUser: User

    init {


        viewModelScope.launch(Dispatchers.IO) {

            currentUser = database.userDatabaseDao.get(uid).first()

            when(currentUser.getRoleEnum()){
                Role.MANAGER -> _users.postValue(database.userDatabaseDao.getAllUsers())
                else -> _tests.postValue(database.testDatabaseDao.testAll())
            }
        }
    }

    fun onUserClicked(uid: Int) {
        _navigateToUser.value = ListFragmentDirections.actionListFragmentToUserSettingFragment(uid)
    }

}