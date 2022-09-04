package com.example.apiwithhilt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apiwithhilt.databinding.ItemLoadingStateBinding
import com.example.apiwithhilt.utils.visible

class UserLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UserLoadStateAdapter.UserLoadStateVIewHolder>() {

    inner class UserLoadStateVIewHolder(
        private val binding: ItemLoadingStateBinding,
        private val retry: () -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {

            if (loadState is LoadState.Error) {
                binding.tvError.text = loadState.error.localizedMessage
            }
            binding.apply {
                progressbar.visible(loadState is LoadState.Loading)
                btnRetry.visible(loadState is LoadState.Error)
                tvError.visible(loadState is LoadState.Error)
                btnRetry.setOnClickListener {
                    retry
                }
                progressbar.visibility = View.VISIBLE
            }
        }
    }

    override fun onBindViewHolder(holder: UserLoadStateVIewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): UserLoadStateVIewHolder {
        return UserLoadStateVIewHolder(ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false), retry)
    }
}