package com.gurudev.junotes.RequestModel

data class SupportRequestModel(
    val email: String,
    val message: String,
    val name: String,
    val userId: String
)