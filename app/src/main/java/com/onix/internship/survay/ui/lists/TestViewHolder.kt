package com.onix.internship.survay.ui.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.survay.database.test.Test
import com.onix.internship.survay.databinding.TestItemViewBinding

class TestViewHolder private constructor(val binding: TestItemViewBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Test, clickListener: TestListener) {
        binding.test = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TestViewHolder {
            val binding = TestItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TestViewHolder(binding)
        }
    }
}