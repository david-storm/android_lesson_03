package com.onix.internship.survay.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.survay.database.user.User
import com.onix.internship.survay.databinding.UserItemViewBinding

class UserAdapter(val clickListener: UserListener) : ListAdapter<User, ViewHolder>(UserDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

}

class ViewHolder private constructor(val binding: UserItemViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: User, clickListener: UserListener) {
        binding.user = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = UserItemViewBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}


class UserDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.getUid() == newItem.getUid()
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}

class UserListener(val clickListener: (uid: Int) -> Unit){
    fun onClick(user: User) = clickListener(user.getUid())
}
