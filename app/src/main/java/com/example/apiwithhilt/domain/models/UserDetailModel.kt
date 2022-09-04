package com.example.apiwithhilt.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class UserDetailModel(
    val data: Data?,
){
    @JsonClass(generateAdapter = true)
    data class Data(
        val avatar: String?,
        val email: String?,
        @Json(name = "first_name")
        val firstName: String?,
        val id: Int?,
        @Json(name = "last_name")
        val lastName: String?
    )
}