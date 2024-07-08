package com.gurudev.junotes.ResponseModel.Profile

data class CONTENT(
    val email: String,
    val id: Int,
    val isSolved: Boolean,
    val message: String,
    val name: String,
    val userId: Int
)