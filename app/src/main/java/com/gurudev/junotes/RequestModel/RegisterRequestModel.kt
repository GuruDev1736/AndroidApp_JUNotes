package com.gurudev.junotes.RequestModel

data class RegisterRequestModel(
    val email: String,
    val fullName: String,
    val password: String,
    val phoneNo: String,
    val year: Int
)