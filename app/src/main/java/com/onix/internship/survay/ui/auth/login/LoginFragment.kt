package com.onix.internship.survay.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.onix.internship.survay.database.AppDatabase
import com.onix.internship.survay.databinding.FragmentLoginBinding
import kotlinx.coroutines.*

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).userDatabaseDao

        val viewModel =
            ViewModelProvider(this, LoginViewModelFactory(dataSource, application))
                .get(LoginViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        lifecycleScope.launch(Dispatchers.IO) {

            waitTime(5)
            val update = async { insertDefaultLogin(viewModel) }
            if(update.await()){
                binding.invalidateAll()
            }
        }
        viewModel.navigationLiveEvent.observe(viewLifecycleOwner, ::navigate)

    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    private suspend fun waitTime(sec: Int) {
        delay((1000 * sec).toLong())
    }


    private fun insertDefaultLogin(model: LoginViewModel): Boolean {
        model.apply {
            if (login.isEmpty()) {
                login = "Jo"
                return true
            }
        }
        return false
    }
}