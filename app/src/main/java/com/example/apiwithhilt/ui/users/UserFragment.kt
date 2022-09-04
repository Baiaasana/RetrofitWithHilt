package com.example.apiwithhilt.ui.users

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiwithhilt.adapters.UserLoadStateAdapter
import com.example.apiwithhilt.adapters.UsersAdapter
import com.example.apiwithhilt.ui.BaseFragment
import com.example.apiwithhilt.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UsersAdapter

    override fun listeners() {
    }

    override fun init() {
        binding.progressBar.isVisible = true
        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            userAdapter = UsersAdapter().apply {
                userItemOnClick = {
                    findNavController().navigate(UserFragmentDirections.actionUserFragmentToUserDetailsFragment(
                        it))
                }
            }
            adapter = userAdapter.withLoadStateHeaderAndFooter(
                header = UserLoadStateAdapter{userAdapter.retry()},
                footer = UserLoadStateAdapter{userAdapter.retry()}
            )
            setHasFixedSize(true)
        }
    }

    override fun observers() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usersFlow().collectLatest {
                    binding.progressBar.isVisible = false
                    userAdapter.submitData(it)
                }
            }
        }
    }
}