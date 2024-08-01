package com.gurudev.junotes.ResponseModel.Notes

data class CreateSubjectResponseModel(
    val CONTENT: SubjectResponse,
    val MSG: String,
    val STS: String
)

data class SubjectResponse(
    val id: Int,
    val subDescription: String,
    val subName: String,
    val yearId: Int
)