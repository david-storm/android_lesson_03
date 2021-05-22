package com.onix.internship.survay.ui.list

import android.os.Bundle
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
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.databinding.FragmentUserListBinding
import com.onix.internship.survay.ui.lists.AppAdapter
import com.onix.internship.survay.ui.lists.TestListener
import com.onix.internship.survay.ui.lists.UserListener
import com.onix.internship.survay.ui.user.list.UserListViewModel

class ListFragment : Fragment() {

    private val args: UserListFragmentArgs by navArgs()
    private lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity).supportActionBar!!.show()

        binding = FragmentUserListBinding.inflate(inflater)

        val dataSource = AppDatabase.getInstance(requireContext())
        val viewModel =
            ViewModelProvider(this, UserListViewModelFactory(dataSource, args.uid))
                .get(UserListViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = AppAdapter(
            UserListener { uid -> viewModel.onUserClicked(uid) },
            TestListener { id -> viewModel.onTestClicked(id) }
        )

//        val manager = LinearLayoutManager(activity)
//        binding.listUsers.layoutManager = manager

        binding.listUsers.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitDataList(it)
            }
        })

        viewModel.navigate.observe(viewLifecycleOwner, ::navigate)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

}