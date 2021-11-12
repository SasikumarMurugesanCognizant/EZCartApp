package com.cts.ezcartapp.domain.model

import android.util.Patterns


data class LoginUser(var EmailAddress: String?, var Password: String?) {
    fun isEmailValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(EmailAddress).matches()
    }

    fun isPasswordLengthGreaterThan5(): Boolean {
        return Password!!.length > 5
    }
}