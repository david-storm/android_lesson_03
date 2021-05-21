package com.onix.internship.survay.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.survay.R
import com.onix.internship.survay.database.test.Test
import com.onix.internship.survay.database.user.User
import com.onix.internship.survay.databinding.TestItemViewBinding
import com.onix.internship.survay.databinding.UserItemViewBinding


private const val ITEM_VIEW_TYPE_USER = 0
private const val ITEM_VIEW_TYPE_TEST = 1

class UserAdapter(private val userListener: UserListener, private val testListener: TestListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(UserDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val userItem = getItem(position) as DataItem.UserItem
                holder.bind(userItem.user, userListener)
            }
            is TestViewHolder -> {
                val testItem = getItem(position) as DataItem.TestItem
                holder.bind(testItem.test, testListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_USER -> ViewHolder.from(parent)
            ITEM_VIEW_TYPE_TEST -> TestViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    fun submitDataList(list: List<Any>) {
        val items = when (list.first()) {
            is User -> list.map{ DataItem.UserItem(it as User) }
            is Test -> list.map{ DataItem.TestItem(it as Test) }
            else -> listOf()
        }
        submitList(items)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.UserItem -> ITEM_VIEW_TYPE_USER
            is DataItem.TestItem -> ITEM_VIEW_TYPE_TEST
        }
    }

}

class TestViewHolder private constructor(val binding: TestItemViewBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Test, clickListener: TestListener) {
        binding.test = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TestViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TestItemViewBinding.inflate(layoutInflater, parent, false)
            return TestViewHolder(binding)
        }
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


class UserDiffCallback : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

class UserListener(val clickListener: (uid: Int) -> Unit) {
    fun onClick(user: User) = clickListener(user.getUid())
}

class TestListener(val clickListener: (id: Int) -> Unit) {
    fun onClick(test: Test) = clickListener(test.id)
}

sealed class DataItem {

    abstract val id: Int

    data class UserItem(val user: User) : DataItem() {
        override val id = user.getUid()
    }

    data class TestItem(val test: Test) : DataItem() {
        override val id = test.id
    }

}