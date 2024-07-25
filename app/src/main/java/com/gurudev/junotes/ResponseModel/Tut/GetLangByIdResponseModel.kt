package com.gurudev.junotes.ResponseModel.Tut

data class GetLangByIdResponseModel(
    val CONTENT: Language,
    val MSG: String,
    val STS: String
)

data class Language(
    val id: Int,
    val languageDescription: String,
    val languageName: String,
    val languageUrl: String
)