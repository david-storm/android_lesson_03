package com.onix.internship.survay.auth.register

import android.app.Application
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.onix.internship.survay.R
import com.onix.internship.survay.auth.pager.PagerFragmentDirections
import com.onix.internship.survay.common.SingleLiveEvent
import com.onix.internship.survay.common.hashPassword
import com.onix.internship.survay.database.user.User
import com.onix.internship.survay.database.user.UserDatabaseDao
import kotlinx.coroutines.launch

class RegisterViewModel(private val database: UserDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    var user = User()

    private val _errorFirstName = MutableLiveData<Int>()
    val errorFirstName: LiveData<Int> = _errorFirstName

    private val _errorSecondName = MutableLiveData<Int>()
    val errorSecondName: LiveData<Int> = _errorSecondName

    private val _errorLogin = MutableLiveData<Int>()
    val errorLogin: LiveData<Int> = _errorLogin

    private val _errorPassword = MutableLiveData<Int>()
    val errorPassword: LiveData<Int> = _errorPassword

    private val _errorPasswordConfirm = MutableLiveData<Int>()
    val errorPasswordConfirm: LiveData<Int> = _errorPasswordConfirm

    private val _navigationLiveEvent = SingleLiveEvent<NavDirections>()
    val navigationLiveEvent: LiveData<NavDirections> = _navigationLiveEvent

    fun onRegister() {

        if (isEmpty() || !checkPassword() || !confirmPassword()) {
            resetPassword()
            return
        }

        viewModelScope.launch {
            if (loginExists()) {
                resetPassword()
                return@launch
            }

            if (database.getAllUsers().isNullOrEmpty()) {
                user.setRole(0)
            } else {
                user.setRole(2)
            }

            user.setPassword(hashPassword(user.getPassword()))

            database.insert(user)

            val newUser = database.get(user.getLogin()) ?: return@launch

            _navigationLiveEvent.value =
                PagerFragmentDirections.actionPagerFragmentToListFragment(newUser.getUid())
        }
    }


    private suspend fun loginExists(): Boolean {

        database.get(user.getLogin()) ?: return false
        _errorLogin.value = R.string.error_login_exist
        return true

    }

    private fun confirmPassword(): Boolean {
        if (user.getPassword() == user.getPasswordConfirm()) {
            return true
        }
        _errorPasswordConfirm.value = R.string.error_confirm_password
        user.setPassword("")
        user.setPasswordConfirm("")
        return false
    }

    private fun checkPassword(): Boolean {
        val password = user.getPassword()

        if (password.length < 8) {
            _errorPassword.value = R.string.error_short_password
            return false
        }

        if (!password.any { it.isLetter() } || !password.any { it.isDigit() }) {
            _errorPassword.value = R.string.error_regex_password
            return false
        }

        return true
    }

    private fun checkEmptyField(field: String): Int {
        if (field.isEmpty()) {
            return R.string.error_empty_field
        }
        return 0
    }

    private fun isEmpty(): Boolean {
        _errorFirstName.value = checkEmptyField(user.getFirstName())
        _errorSecondName.value = checkEmptyField(user.getSecondName())
        _errorLogin.value = checkEmptyField(user.getLogin())
        _errorPassword.value = checkEmptyField(user.getPassword())
        _errorPasswordConfirm.value = checkEmptyField(user.getPasswordConfirm())

        return _errorFirstName.value != 0 || _errorSecondName.value != 0 || _errorLogin.value != 0 ||
                _errorPassword.value != 0 || _errorPasswordConfirm.value != 0
    }

    private fun resetPassword() {
        user.setPassword("")
        user.setPasswordConfirm("")
    }

}
