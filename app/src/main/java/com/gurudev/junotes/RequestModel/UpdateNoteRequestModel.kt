package com.gurudev.junotes.RequestModel

data class UpdateNoteRequestModel(
    val subjectId: String,
    val title: String,
    val url: String,
    val userId : String,
    val imageUrl : String
)