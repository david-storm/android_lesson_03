package com.onix.internship.survay.ui.user.list

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.onix.internship.survay.BR


data class UserModel (
    private var check: Boolean = true
        ) : BaseObservable() {



    @Bindable
    fun getCheck(): Boolean{
        return check
    }

    fun setCheck(field: Boolean){
        check = field
        notifyPropertyChanged(BR.check)

    }

}
