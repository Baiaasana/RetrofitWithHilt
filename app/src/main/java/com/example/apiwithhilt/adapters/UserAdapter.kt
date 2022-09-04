package com.example.apiwithhilt.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apiwithhilt.databinding.ItemUserBinding
import com.example.apiwithhilt.domain.models.UserModel
import com.example.apiwithhilt.extensions.setImage

typealias UserItemOnClick = (id: Int) -> Unit

class UsersAdapter : PagingDataAdapter<UserModel.Data, UsersAdapter.UserViewHolder>(
    DiffCallback()
) {

    var userItemOnClick: UserItemOnClick? = null
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind() {
            val user = getItem(bindingAdapterPosition)
            binding
            binding.profileImage.setImage(user?.avatar)
            binding.tvName.text =
                user?.firstName.toString().plus(" ").plus(user?.lastName.toString())
            binding.tvEmail.text = user?.email.toString()
            binding.root.setOnClickListener {
                user?.id?.let { it -> userItemOnClick?.invoke(it) }
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<UserModel.Data>() {
    override fun areItemsTheSame(oldItem: UserModel.Data, newItem: UserModel.Data) =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: UserModel.Data, newItem: UserModel.Data): Boolean {
        return oldItem == newItem
    }
}