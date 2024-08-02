package com.gurudev.junotes.RequestModel

data class CreateNoteRequestModel(
    val imageUrl: String,
    val subjectId: String,
    val title: String,
    val url: String,
    val userId: String
)