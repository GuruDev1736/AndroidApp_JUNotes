package com.gurudev.junotes.ResponseModel.Tut

data class GetAllLanguageResponseModel(
    val CONTENT: List<Languages>,
    val MSG: String,
    val STS: String
)

data class Languages(
    val id: Int,
    val languageDescription: String,
    val languageName: String,
    val languageUrl: String
)