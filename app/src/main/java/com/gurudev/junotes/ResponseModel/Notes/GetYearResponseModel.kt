package com.gurudev.junotes.ResponseModel.Notes

data class GetYearResponseModel(
    val CONTENT: List<Years>,
    val MSG: String,
    val STS: String
)

data class Years(
    val id: Int,
    val yearDescription: String,
    val yearName: String
)