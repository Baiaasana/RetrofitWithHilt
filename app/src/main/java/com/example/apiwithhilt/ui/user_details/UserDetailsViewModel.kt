package com.example.apiwithhilt.ui.user_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiwithhilt.domain.models.UserDetailModel
import com.example.apiwithhilt.domain.repository.UserRepository
import com.example.apiwithhilt.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

        private val _detailFlow = MutableStateFlow<Resource<UserDetailModel>>(Resource.loading(null))
        val detailFlow = _detailFlow.asStateFlow()

        fun getUserDetails(userId: Int) {
            viewModelScope.launch {
                val data = userRepository.getUserDetails(userId)
                _detailFlow.value = data
            }
        }

}