package com.gurudev.junotes.Constants

import org.intellij.lang.annotations.Pattern

object Validation {

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Regex pattern for a simple phone number validation
        val pattern = Regex("^\\+(?:[0-9] ?){6,14}[0-9]$")
        return pattern.matches(phoneNumber)
    }

    fun isValidEmail(email: String): Boolean {
        val pattern = android.util.Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }



}