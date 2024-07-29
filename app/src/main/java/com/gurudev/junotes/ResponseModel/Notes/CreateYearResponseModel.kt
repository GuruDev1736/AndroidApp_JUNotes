package com.gurudev.junotes.ResponseModel.Notes

data class CreateYearResponseModel(
    val CONTENT: Year,
    val MSG: String,
    val STS: String
)

data class Year(
    val id: Int,
    val yearDescription: String,
    val yearName: String
)