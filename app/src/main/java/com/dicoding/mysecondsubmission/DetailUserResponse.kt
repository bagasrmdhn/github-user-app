package com.dicoding.mysecondsubmission

data class DetailUserResponse(
    val login: String,
    val avatar_url: String,
    val id: Int,
    val following_url : String,
    val followers_url: String,
    val name: String,
    val followers: String,
    val following: String,
    val company: String,
    val location: String

)
