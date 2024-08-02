package com.gurudev.junotes.ResponseModel.Notes

data class CreateNoteResponseModel(
    val CONTENT: Note,
    val MSG: String,
    val STS: String
)

data class Note(
    val date: Long,
    val id: Int,
    val imageUrl: String,
    val subjectId: Int,
    val title: String,
    val url: String,
    val user: createdBy,
    val userId: Int
)

data class createdBy(
    val email: String,
    val fullName: String,
    val id: Int,
    val password: String,
    val phoneNo: String,
    val profile_pic: Any,
    val year: Int,
    val yearName: String
)