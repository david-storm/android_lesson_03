package com.onix.internship.survay.ui.view.binding

import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.onix.internship.survay.R
import com.onix.internship.survay.common.ErrorStates
import com.onix.internship.survay.common.Role
import com.onix.internship.survay.database.user.User


@BindingAdapter("errorResource")
fun TextInputLayout.errorMessage(errorStates: ErrorStates) {
    error = when (errorStates) {
        ErrorStates.NONE -> ""
        ErrorStates.EXIST_LOGIN -> context.getString(R.string.error_login_exist)
        ErrorStates.INCORRECT_PASSWORD -> context.getString(R.string.error_regex_password)
        ErrorStates.EMPTY_FIELD -> context.getString(R.string.error_empty_field)
        ErrorStates.NO_SUCH_USER -> context.getString(R.string.error_login_or_password)
        ErrorStates.PASSWORDS_CONFIRM -> context.getString(R.string.error_confirm_password)
        ErrorStates.PASSWORD_SHORT -> context.getString(R.string.error_short_password)

    }
}

@BindingAdapter("role")
fun TextView.role(item: Int?) {
    item?.let {
        Log.i("test", "$item")
        text =
            when (item) {
//                Role.ADMIN -> R.string.role_admin
//                Role.MANAGER -> R.string.role_manager
//                Role.USER -> R.string.role_user
                0 -> resources.getString(R.string.role_admin)
                1 ->  resources.getString(R.string.role_manager)
                2 ->  resources.getString(R.string.role_user)
                else ->  resources.getString(R.string.role_other)

            }
    }
}

@BindingAdapter("buttonAgree")
fun TextView.buttonAgree(agree: Boolean) {
   text = when(agree){
       true -> "agree"
       false -> "not agree"
   }
}

@BindingAdapter("setFullName")
fun TextView.setFullName(item: User?) {
    item?.let {
        text = item.getFirstName().plus(" ").plus(item.getSecondName())
    }
}

@BindingAdapter("viewRole")
fun CheckBox.viewRole( item: User?) {
    item?.let {
        isChecked = when(item.getRoleEnum()){
            Role.MANAGER -> true
            else -> false
        }
    }
}

//@BindingAdapter("viewRole")
//fun viewRole(view :CheckBox, item: User){
////    view.isChecked = true
////    setChecked = when(item.getRole()){
////        1 -> true
////        else -> false
////    }
//}