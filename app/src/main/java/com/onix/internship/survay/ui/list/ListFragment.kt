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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private val args: ListFragmentArgs by navArgs()
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity).supportActionBar!!.show()

        binding = FragmentListBinding.inflate(inflater)

        val dataSource = AppDatabase.getInstance(requireContext())
        val viewModel =
            ViewModelProvider(this, ListViewModelFactory(dataSource))
                .get(ListViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = UserAdapter(UserListener { uid ->
            viewModel.onUserClicked(uid)
        })

//        val manager = LinearLayoutManager(activity)
//        binding.listUsers.layoutManager = manager

        binding.listUsers.adapter = adapter

        viewModel.users.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToUser.observe(viewLifecycleOwner, ::navigate)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

}