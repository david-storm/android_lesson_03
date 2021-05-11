package com.onix.internship.survay.bindingadapter


import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorResource")
fun TextInputLayout.errorMessage(resource: Int) {
    error = if (resource != 0) {
        resources.getString(resource)
    } else ""
}