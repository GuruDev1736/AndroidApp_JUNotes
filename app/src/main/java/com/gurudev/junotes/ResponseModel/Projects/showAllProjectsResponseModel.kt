package com.gurudev.junotes.ResponseModel.Projects


data class showAllProjectsResponseModel(
    val CONTENT: List<CONTENT>,
    val MSG: String,
    val STS: String
)

data class CONTENT(
    val feature: String,
    val githubUrl: String,
    val id: Int,
    val imageUrl: String,
    val language: String,
    val projectDescription: String,
    val projectTitle: String,
    val technologyUsed: String,
    val userId: Int
)