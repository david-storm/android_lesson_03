package com.onix.internship.survay.ui.auth.register

import android.app.Application
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.onix.internship.survay.ui.auth.pager.PagerFragmentDirections
import com.onix.internship.survay.common.ErrorStates
import com.onix.internship.survay.common.Role
import com.onix.internship.survay.common.SingleLiveEvent
import com.onix.internship.survay.common.hashPassword
import com.onix.internship.survay.database.user.User
import com.onix.internship.survay.database.user.UserDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val database: UserDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    var user = User()

    private val _errorFirstName = MutableLiveData(ErrorStates.NONE)
    val errorFirstName: LiveData<ErrorStates> = _errorFirstName

    private val _errorSecondName = MutableLiveData(ErrorStates.NONE)
    val errorSecondName: LiveData<ErrorStates> = _errorSecondName

    private val _errorLogin = MutableLiveData(ErrorStates.NONE)
    val errorLogin: LiveData<ErrorStates> = _errorLogin

    private val _errorPassword = MutableLiveData(ErrorStates.NONE)
    val errorPassword: LiveData<ErrorStates> = _errorPassword

    private val _errorPasswordConfirm = MutableLiveData(ErrorStates.NONE)
    val errorPasswordConfirm: LiveData<ErrorStates> = _errorPasswordConfirm

    private val _navigationLiveEvent = SingleLiveEvent<NavDirections>()
    val navigationLiveEvent: LiveData<NavDirections> = _navigationLiveEvent

    private var hasEmptyFields = false

    fun onRegister() {

        if (isEmpty() || !checkPassword() || !confirmPassword()) {
            resetPasswords()
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (loginExists()) {
                resetPasswords()
                return@launch
            }

            if (database.getAllUsers().isNullOrEmpty()) {
                user.setRoleEnum(Role.ADMIN)
            } else {
                user.setRoleEnum(Role.USER)
            }

            user.setPassword(hashPassword(user.getPassword()))

            database.insert(user)

            val newUser = database.get(user.getLogin())

            _navigationLiveEvent.postValue(
                    PagerFragmentDirections.actionPagerFragmentToTestListFragment(newUser[0].getUid())
            )
        }
    }


    private suspend fun loginExists(): Boolean {

        return if (database.get(user.getLogin()).isNotEmpty()) {
            _errorLogin.value = ErrorStates.EXIST_LOGIN
            true
        } else {
            false
        }
    }

    private fun confirmPassword(): Boolean {
        if (user.getPassword() == user.getPasswordConfirm()) {
            return true
        }
        _errorPasswordConfirm.value = ErrorStates.PASSWORDS_CONFIRM
        resetPasswords()
        return false
    }

    private fun checkPassword(): Boolean {
        val password = user.getPassword()

        if (password.length < 8) {
            _errorPassword.value = ErrorStates.PASSWORD_SHORT
            return false
        }

        if (!password.any { it.isLetter() } || !password.any { it.isDigit() }) {
            _errorPassword.value = ErrorStates.INCORRECT_PASSWORD
            return false
        }

        return true
    }

    private fun checkEmptyField(field: String): ErrorStates {
        if (field.isEmpty()) {
            hasEmptyFields = true
            return ErrorStates.EMPTY_FIELD
        }
        return ErrorStates.NONE
    }

    private fun isEmpty(): Boolean {
        hasEmptyFields = false
        _errorFirstName.value = checkEmptyField(user.getFirstName())
        _errorSecondName.value = checkEmptyField(user.getSecondName())
        _errorLogin.value = checkEmptyField(user.getLogin())
        _errorPassword.value = checkEmptyField(user.getPassword())
        _errorPasswordConfirm.value = checkEmptyField(user.getPasswordConfirm())

        return hasEmptyFields
    }

    private fun resetPasswords() {
        user.setPassword("")
        user.setPasswordConfirm("")
    }
}
