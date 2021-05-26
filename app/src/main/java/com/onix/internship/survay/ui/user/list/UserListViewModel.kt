package com.onix.internship.survay.ui.user.list

import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.google.android.material.snackbar.Snackbar
import com.onix.internship.survay.BR
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

    var model: UserModel = UserModel()


//    private var _modelCheck : MutableLiveData<Boolean> = MutableLiveData(model.check)
//    val modelCheck: LiveData<Boolean> = _modelCheck

    private val _checkModel = MutableLiveData(false)
    val checkModel: LiveData<Boolean> = _checkModel


    val change: Change = object : Change {
        override fun onTextChange(value: Int) {
            viewModelSnack(value)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {

            currentUser = database.userDatabaseDao.get(uid).first()
            if (testIdSelected != 0) {
                selectedTest = database.testDatabaseDao.getTest(testIdSelected).first()
            }
            val us = database.userDatabaseDao.getAllUsers()
            us.map { user ->  user.setOnChangeListener(change) }
            _users.postValue(us)
        }

    }


    fun finishviewModel() {
        _checkModel.value = false
    }

    fun viewModelSnack(value: Int){
       Log.i("test", value.toString().plus(" test"))
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

    fun onChangeRole(uid: Int, checked: Boolean = true) {
        Log.i("ROle", "chenge".plus(uid.toString()).plus(checked.toString()))
    }
}
