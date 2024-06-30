package com.gurudev.junotes.ResponseModel.Login

data class CONTENT(
    val token: String,
    val userId: Int,
    val yearId : Int,
    val userName: String,
    val userProfilePic: Any,
    val userRole: String
)