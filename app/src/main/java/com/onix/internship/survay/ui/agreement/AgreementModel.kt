package com.onix.internship.survay.ui.agreement

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.onix.internship.survay.BR

data class AgreementModel(
    private var _userAgreement: Boolean = false,
    private var _checkBoxTwo: Boolean = false
) : BaseObservable() {

    @get:Bindable
    var userAgreement: Boolean = _userAgreement
        set(value) {
            _userAgreement = value
            field = value
            notifyPropertyChanged(BR.userAgreement)
        }


    @get:Bindable
    var checkBoxTwo: Boolean = _checkBoxTwo
        set(value) {
            _checkBoxTwo = value
            field = value
            notifyPropertyChanged(BR.checkBoxTwo)
        }

}