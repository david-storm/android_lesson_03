package com.onix.internship.survay.ui.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.survay.database.user.User
import com.onix.internship.survay.databinding.UserItemViewBinding

class UserViewHolder private constructor(val binding: UserItemViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: User, listener: ((Int) -> Unit)?) {
        binding.user = item
        binding.login.setOnClickListener { listener?.invoke(item.getUid()) }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): UserViewHolder {
            val binding = UserItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return UserViewHolder(binding)
        }
    }
}