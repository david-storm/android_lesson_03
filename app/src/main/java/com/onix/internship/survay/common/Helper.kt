package com.onix.internship.survay.common

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.math.BigInteger
import java.security.MessageDigest

fun hashPassword(password: String): String = BigInteger(
    1, MessageDigest.getInstance("MD5").digest(password.toByteArray())
).toString(16).padStart(32, '0')
