package com.onix.internship.survay.ui.user.list

import android.util.Log

data class UserModel (
    var check: Boolean = true
        ) {

    fun changeCheck(){
        check = !check
        Log.i("test", check.toString())
    }

    fun tttt(){
        check = !check
        Log.i("test", check.toString())
    }
}