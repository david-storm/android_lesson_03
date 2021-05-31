package com.onix.internship.survay.ui.user.list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.onix.internship.survay.R
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.databinding.FragmentUserListBinding
import com.onix.internship.survay.ui.list.UserListViewModelFactory
import com.onix.internship.survay.ui.lists.AppAdapter
import com.onix.internship.survay.ui.logout.LogoutDialog

class UserListFragment : Fragment() {

    private val args: UserListFragmentArgs by navArgs()
    private lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserListBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.add("role")

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.role -> {


//                Log.i("test", "role")
//                navigate(UserListFragmentDirections.actionUserListFragmentToLogoutDialog())
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val dataSource = AppDatabase.getInstance(requireContext())
            val viewModel =
                ViewModelProvider(
                    this,
                    UserListViewModelFactory(dataSource, args.uid, args.testSelected)
                )
                    .get(UserListViewModel::class.java)

            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)


            binding.viewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
            binding.listUsers.layoutManager = LinearLayoutManager(requireContext())

            val adapter = AppAdapter { uid -> viewModel.onUserClicked(uid) }

            binding.listUsers.adapter = adapter

            viewModel.users.observe(viewLifecycleOwner, Observer {
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