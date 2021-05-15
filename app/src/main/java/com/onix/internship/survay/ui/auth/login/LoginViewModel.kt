package com.onix.internship.survay.ui.auth.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.onix.internship.survay.ui.auth.pager.PagerFragmentDirections
import com.onix.internship.survay.common.ErrorStates
import com.onix.internship.survay.common.SingleLiveEvent
import com.onix.internship.survay.database.user.UserDatabaseDao

class LoginViewModel(private val database: UserDatabaseDao, application: Application) :
    AndroidViewModel(application) {


    private val _navigationLiveEvent = SingleLiveEvent<NavDirections>()
    val navigationLiveEvent: LiveData<NavDirections> = _navigationLiveEvent

    private val _errorLogin = MutableLiveData(ErrorStates.NONE)
    val errorLogin: LiveData<ErrorStates> = _errorLogin

    private val _errorPassword = MutableLiveData(ErrorStates.NONE)
    val errorPassword: LiveData<ErrorStates> = _errorPassword

    var login = ""
    var password = ""

    fun onLogin() {

        _navigationLiveEvent.postValue(
            PagerFragmentDirections.actionPagerFragmentToListFragment(1))

        return
//        if (checkEmptyFieldAndViewError()) {
//            return
//        }
//
//        viewModelScope.launch(Dispatchers.IO) {
//            val user = database.get(login, hashPassword(password))
//
//            if (user.isEmpty()) {
//                _errorLogin.value = ErrorStates.NO_SUCH_USER
//                password = ""
//            } else {
//                _navigationLiveEvent.postValue(
//                    PagerFragmentDirections.actionPagerFragmentToListFragment(user[0].getUid())
//                  )
//            }
//        }
    }


    private fun checkEmptyFieldAndViewError(): Boolean {

        var error = false

        if (login.isEmpty()) {
            error = true
            _errorLogin.value = ErrorStates.EMPTY_FIELD
        } else {
            _errorLogin.value = ErrorStates.NONE
        }

        if (password.isEmpty()) {
            error = true
            _errorPassword.value = ErrorStates.EMPTY_FIELD
        } else {
            _errorPassword.value = ErrorStates.NONE
        }

        return error
    }
}