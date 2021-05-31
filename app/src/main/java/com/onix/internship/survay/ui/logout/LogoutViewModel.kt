package com.onix.internship.survay.ui.logout

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.onix.internship.survay.common.SingleLiveEvent

class LogoutViewModel() :  ViewModel() {

    private val _dialogEvent = SingleLiveEvent<Boolean>()
    val dialogEvent: LiveData<Boolean> = _dialogEvent


    fun onClickButtonYes() {
        _dialogEvent.value = true
    }

    fun onClickButtonNo() {
        _dialogEvent.value = false
    }

}