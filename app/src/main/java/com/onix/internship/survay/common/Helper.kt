package com.onix.internship.survay.common

import java.math.BigInteger
import java.security.MessageDigest

fun hashPassword(password: String): String = BigInteger(
    1, MessageDigest.getInstance("MD5").digest(password.toByteArray())
).toString(16).padStart(32, '0')
