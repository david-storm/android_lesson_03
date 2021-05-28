package com.onix.internship.survay.ui.agreement

import android.util.Log
import android.widget.CompoundButton
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onix.internship.survay.BR

class AgreementViewModel : ViewModel() {

    private val _snackBarShow = MutableLiveData(false)
    val snackBarShow: LiveData<Boolean> = _snackBarShow

    private val _enabledButtonNext = MutableLiveData(true)
    val enabledButtonNext: LiveData<Boolean> = _enabledButtonNext

    private val _countClicked = MutableLiveData(mutableListOf(0, 0))
    val countClicked: LiveData<MutableList<Int>> = _countClicked

    val model = AgreementModel()

    private fun incrementCount(index: Int) {
        val result = _countClicked.value!!
        result[index] = result[index] + 1
        _countClicked.value = result
    }

    fun onCheckedChanged(index: Int, isChecked: Boolean) {
        if (index == 0) {
            Log.i("test-c", "${model.userAgreement}")
            _enabledButtonNext.value = isChecked
        }
        incrementCount(index)
    }


    fun agree() {
        model.apply {
            Log.i("test-b", "$userAgreement")
            userAgreement = !userAgreement
        }
    }

    fun showBar() {
        _snackBarShow.value = true
    }

    fun showBarFinished() {
        _snackBarShow.value = false
    }
}