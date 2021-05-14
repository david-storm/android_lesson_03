package com.onix.internship.survay.bindingadapter


import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.onix.internship.survay.R
import com.onix.internship.survay.common.ErrorStates

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