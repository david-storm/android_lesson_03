package com.onix.internship.survay.ui.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.survay.database.test.Test
import com.onix.internship.survay.database.user.User
import com.onix.internship.survay.databinding.TestItemViewBinding
import com.onix.internship.survay.databinding.UserItemViewBinding


private const val ITEM_VIEW_TYPE_USER = 0
private const val ITEM_VIEW_TYPE_TEST = 1

class AppAdapter(private val userListener: UserListener, private val testListener: TestListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(AppDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> {
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
            ITEM_VIEW_TYPE_USER -> UserViewHolder.from(parent)
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