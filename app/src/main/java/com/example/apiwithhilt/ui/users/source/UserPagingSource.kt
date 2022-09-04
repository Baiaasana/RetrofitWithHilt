package com.example.apiwithhilt.ui.users.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.apiwithhilt.domain.models.UserModel
import com.example.apiwithhilt.domain.repository.UserRepository
import com.example.apiwithhilt.utils.Resource

class UserPagingSource(private val userRepository: UserRepository) :
    PagingSource<Int, UserModel.Data>() {
    override fun getRefreshKey(state: PagingState<Int, UserModel.Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel.Data> {
        val pageNumber = params.key ?: 1
        val response = userRepository.getUsers(pageNumber)
        return when (response.status) {
            Resource.Status.SUCCESS -> {
                var prev: Int? = null
                var next: Int? = null

                if (response.data!!.totalPages!! > pageNumber)
                    next = pageNumber + 1
                if (pageNumber != 1)
                    prev = pageNumber - 1
                LoadResult.Page(data = response.data.data!!, prevKey = prev, nextKey = next)
            }
            Resource.Status.ERROR -> {
                LoadResult.Error(Throwable())
            }
            Resource.Status.LOADING -> {
                LoadResult.Error(Throwable())
            }
        }
    }
}