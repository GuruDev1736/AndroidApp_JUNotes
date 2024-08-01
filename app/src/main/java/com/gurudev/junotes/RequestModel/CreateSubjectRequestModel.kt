package com.gurudev.junotes.RequestModel

data class CreateSubjectRequestModel(
    val subDescription: String,
    val subName: String,
    val yearId: Int
)