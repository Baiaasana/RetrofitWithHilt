package com.example.apiwithhilt.domain.repository

import com.example.apiwithhilt.data.ApiService
import com.example.apiwithhilt.domain.models.UserDetailModel
import com.example.apiwithhilt.domain.models.UserModel
import com.example.apiwithhilt.utils.Resource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService,
) {
    suspend fun getUsers(page: Int): Resource<UserModel> {
        return try {
            Resource.loading(null)
            val response = api.getUsers(page)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.success(result)
            } else {
                Resource.error(response.message())
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
    }

    suspend fun getUserDetails(id:Int) : Resource<UserDetailModel> {
        return try {
            Resource.loading(null)
            val response = api.getUserDetails(id)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.success(result)
            } else {
                Resource.error(response.message())
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
    }
}