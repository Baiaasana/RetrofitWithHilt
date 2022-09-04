package com.example.apiwithhilt.data

import com.example.apiwithhilt.domain.models.UserDetailModel
import com.example.apiwithhilt.domain.models.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<UserModel>

    @GET("users/{id}")
    suspend fun getUserDetails(@Path("id") id: Int): Response<UserDetailModel>

}