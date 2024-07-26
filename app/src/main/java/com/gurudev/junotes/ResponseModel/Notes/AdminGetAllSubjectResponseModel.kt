package com.gurudev.junotes.ResponseModel.Notes

data class AdminGetAllSubjectResponseModel(
    val CONTENT: List<Subject>,
    val MSG: String,
    val STS: String
)

data class Subject(
    val id: Int,
    val subDescription: String,
    val subName: String,
    val yearId: Int
)