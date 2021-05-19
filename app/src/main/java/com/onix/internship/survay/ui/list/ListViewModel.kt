package com.onix.internship.survay.ui.list

import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.onix.internship.survay.common.SingleLiveEvent
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.database.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(private val database: AppDatabase) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _navigateToUser = SingleLiveEvent<NavDirections>()
    val navigateToUser : LiveData<NavDirections> = _navigateToUser

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _users.postValue(database.userDatabaseDao.getAllUsers())
        }
    }

    fun onUserClicked(uid: Int){
        _navigateToUser.value = ListFragmentDirections.actionListFragmentToUserSettingFragment(uid)
    }

}