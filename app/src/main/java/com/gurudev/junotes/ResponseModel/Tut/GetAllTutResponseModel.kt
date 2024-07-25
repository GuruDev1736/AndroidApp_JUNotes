package com.gurudev.junotes.ResponseModel.Tut

data class GetAllTutResponseModel(
    val CONTENT: List<CONTENT>,
    val MSG: String,
    val STS: String
)

data class CONTENT(
    val createrLogo: String,
    val createrName: String,
    val date: Long,
    val id: Int,
    val langId: Int,
    val notesLink: String,
    val playlistLink: String,
    val title: String,
    val userId: Int
)