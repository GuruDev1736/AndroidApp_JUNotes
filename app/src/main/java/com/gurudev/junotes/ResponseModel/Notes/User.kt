package com.gurudev.junotes.ResponseModel.Notes

data class User(
    val email: String,
    val fullName: String,
    val id: Int,
    val password: String,
    val phoneNo: String,
    val profile_pic: Any,
    val year: Int,
    val yearName: String
)