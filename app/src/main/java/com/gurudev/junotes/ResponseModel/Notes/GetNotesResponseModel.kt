package com.gurudev.junotes.ResponseModel.Notes

data class GetNotesResponseModel(
    val CONTENT: List<CONTENTX>,
    val MSG: String,
    val STS: String
)