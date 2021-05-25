package com.onix.internship.survay.ui.user.list

import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.onix.internship.survay.common.Role
import com.onix.internship.survay.common.SingleLiveEvent
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.database.test.Test
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

    private val _model : MutableLiveData<UserModel> = MutableLiveData()
    val model: LiveData<UserModel> = _model

    val currentMod = Transformations.map(model) {
        check -> check
    }

    private fun currentModel() : UserModel = _model.value!!

    init {
        _model.value = UserModel()
        viewModelScope.launch(Dispatchers.IO) {

            currentUser = database.userDatabaseDao.get(uid).first()
            if (testIdSelected != 0) {
                selectedTest = database.testDatabaseDao.getTest(testIdSelected).first()
            }
            _users.postValue(database.userDatabaseDao.getAllUsers())
        }
    }
    fun changeModel() {
//       model.value = currentModel().copy(check = !currentModel().check)
    }

    fun saveModel(data : UserModel) {
        Log.i("test-2", data.check.toString())
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
