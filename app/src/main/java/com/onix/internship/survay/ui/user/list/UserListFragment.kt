package com.onix.internship.survay.ui.user.list

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
import com.google.android.material.snackbar.Snackbar
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.databinding.FragmentUserListBinding
import com.onix.internship.survay.ui.list.UserListViewModelFactory
import com.onix.internship.survay.ui.lists.AppAdapter

class UserListFragment : Fragment() {

    private val args: UserListFragmentArgs by navArgs()
    private lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity).supportActionBar!!.show()
        binding = FragmentUserListBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSource = AppDatabase.getInstance(requireContext())
        val viewModel =
            ViewModelProvider(this, UserListViewModelFactory(dataSource, args.uid, args.testSelected))
                .get(UserListViewModel::class.java)

//        val nameObserver = Observer<Boolean> { data ->
//            Log.i("test", data.toString().plus(" 25"))
//        }

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
//        viewModel.modelCheck.observe(viewLifecycleOwner, nameObserver)


        viewModel.checkModel.observe(viewLifecycleOwner, {
            if(it == true) {
                Snackbar.make(
                    view,
                    viewModel.model.getCheck().toString().plus(" test"),
                    Snackbar.LENGTH_LONG
                )
                    .show()
                viewModel.finishviewModel()
            }
        })

        viewModel.navigate.observe(viewLifecycleOwner, ::navigate)

    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

}