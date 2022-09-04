package com.example.apiwithhilt.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.apiwithhilt.domain.repository.UserRepository
import com.example.apiwithhilt.ui.users.source.UserPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    fun usersFlow() = Pager(config = PagingConfig(pageSize = 1, maxSize = 200),
        pagingSourceFactory = { UserPagingSource(userRepository) })
        .flow.cachedIn(viewModelScope)
}
