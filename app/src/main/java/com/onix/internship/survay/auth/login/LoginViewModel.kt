package com.onix.internship.survay.auth.login

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.onix.internship.survay.R
import com.onix.internship.survay.auth.pager.PagerFragmentDirections
import com.onix.internship.survay.common.SingleLiveEvent
import com.onix.internship.survay.common.hashPassword
import com.onix.internship.survay.database.user.UserDatabaseDao
import kotlinx.coroutines.launch

class LoginViewModel(private val database: UserDatabaseDao, application: Application) :
    AndroidViewModel(application) {


    private val _navigationLiveEvent = SingleLiveEvent<NavDirections>()
    val navigationLiveEvent: LiveData<NavDirections> = _navigationLiveEvent

    private val _errorLogin = MutableLiveData<Int>()
    val errorLogin: LiveData<Int> = _errorLogin

    private val _errorPassword = MutableLiveData<Int>()
    val errorPassword: LiveData<Int> = _errorPassword

    val login = ObservableField<String>()

    val password = ObservableField<String>()


    fun onLogin() {

        _navigationLiveEvent.value =
            PagerFragmentDirections.actionPagerFragmentToListFragment(1)

        if (isEmpty()) {
            return
        }

        viewModelScope.launch {
            val user = database.get(login.get()!!)
            if(user == null){
                viewErrorLogin()
                return@launch
            }

            if(user.getPassword() != hashPassword(password.get()!!)){
                viewErrorLogin()
                return@launch
            }


        }
    }


    private fun isEmpty(): Boolean {

        var error = false

        if (login.get().isNullOrEmpty()) {
            error = true
            _errorLogin.value = R.string.error_empty_field
        }

        if (password.get().isNullOrEmpty()) {
            error = true
            password.set("")
            _errorPassword.value = R.string.error_empty_field
        }

        return error
    }

    private fun viewErrorLogin(){
        _errorLogin.value = R.string.error_login_or_password
        password.set("")
    }



}