package com.gurudev.junotes.ResponseModel.Notes

data class CONTENTX(
    val date: Long,
    val id: Int,
    val imageUrl: String,
    val subjectId: Int,
    val title: String,
    val url: String,
    val user: User,
    val userId: Int
)