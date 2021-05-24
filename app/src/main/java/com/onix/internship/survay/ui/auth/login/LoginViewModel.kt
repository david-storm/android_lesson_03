package com.onix.internship.survay.ui.auth.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.onix.internship.survay.ui.auth.pager.PagerFragmentDirections
import com.onix.internship.survay.common.ErrorStates
import com.onix.internship.survay.common.Role
import com.onix.internship.survay.common.SingleLiveEvent
import com.onix.internship.survay.common.hashPassword
import com.onix.internship.survay.database.user.UserDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        val test = 1
        if(test == 1) {
            _navigationLiveEvent.postValue(
                PagerFragmentDirections.actionPagerFragmentToTestListFragment(1)
            )
            return
        }
        if (checkEmptyFieldAndViewError()) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val user = database.get(login, hashPassword(password))

            if (user.isEmpty()) {
                _errorLogin.value = ErrorStates.NO_SUCH_USER
                password = ""
            } else {
                _navigationLiveEvent.postValue(
                    when (user.first().getRoleEnum()) {
                        Role.ADMIN -> PagerFragmentDirections.actionPagerFragmentToTestListFragment(user.first().getUid())
                        Role.USER -> PagerFragmentDirections.actionPagerFragmentToTestListFragment(user.first().getUid())
                        Role.MANAGER -> PagerFragmentDirections.actionPagerFragmentToUserListFragment(user.first().getUid())
                        Role.DEFAULT -> throw ClassCastException("DEFAULT role")
                    }
                )
            }
        }
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