package com.onix.internship.survay.ui.test.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.databinding.FragmentTestListBinding
import com.onix.internship.survay.ui.lists.AppAdapter

class TestListFragment : Fragment() {

    private val args: TestListFragmentArgs by navArgs()
    private lateinit var binding: FragmentTestListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar!!.show()

        binding = FragmentTestListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataSource = AppDatabase.getInstance(requireContext())
        val viewModel =
            ViewModelProvider(this, TestListViewModelFactory(dataSource, args.uid))
                .get(TestListViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner
//        binding.listTests.layoutManager = LinearLayoutManager(requireContext())
        val adapter = AppAdapter { viewModel.onTestClicked(id) }
        binding.listTests.adapter

        viewModel.tests.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitDataList(it)
            }
        })

        viewModel.navigate.observe(viewLifecycleOwner, ::navigate)
    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

}