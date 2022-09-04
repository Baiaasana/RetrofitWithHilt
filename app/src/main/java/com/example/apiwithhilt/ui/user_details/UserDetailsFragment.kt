package com.example.apiwithhilt.ui.user_details

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.apiwithhilt.databinding.FragmentUserDetailsBinding
import com.example.apiwithhilt.extensions.setImage
import com.example.apiwithhilt.ui.BaseFragment
import com.example.apiwithhilt.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsFragment :
    BaseFragment<FragmentUserDetailsBinding>(FragmentUserDetailsBinding::inflate) {

    private val viewModel: UserDetailsViewModel by viewModels()
    private val args: UserDetailsFragmentArgs by navArgs()

    override fun listeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getUserDetails(args.userId)
        }
    }

    override fun init() {
        viewModel.getUserDetails(args.userId)
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailFlow.collect {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            binding.swipeRefresh.isRefreshing = true
                        }
                        Resource.Status.SUCCESS -> {
                            binding.apply {
                                swipeRefresh.isRefreshing = false
                                profileImage.setImage(it.data?.data?.avatar)
                                binding.tvName.text =
                                    it.data?.data?.firstName.plus(it.data?.data?.lastName)
                            }
                        }
                        Resource.Status.ERROR -> {
                            binding.swipeRefresh.isRefreshing = false
                        }
                    }
                }
            }
        }
    }
}