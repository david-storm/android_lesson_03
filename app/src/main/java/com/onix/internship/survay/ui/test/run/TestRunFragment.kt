package com.onix.internship.survay.ui.test.run

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.onix.internship.survay.databinding.FragmentStubBinding
import com.onix.internship.survay.databinding.FragmentTestRunBinding

class TestRunFragment: Fragment() {

    private val viewModel: TestRunViewModel by viewModels()
    private lateinit var binding: FragmentTestRunBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestRunBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }
}