package com.gurudev.junotes.RequestModel

data class changePasswordRequestModel(
    val lastPassword: String,
    val newPassword: String,
    val userId: String
)