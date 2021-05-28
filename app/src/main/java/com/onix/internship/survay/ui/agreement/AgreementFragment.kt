package com.onix.internship.survay.ui.agreement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.onix.internship.survay.databinding.FragmentAgreementBinding
import com.onix.internship.survay.databinding.FragmentStubBinding
import com.onix.internship.survay.generated.callback.OnClickListener
import com.onix.internship.survay.ui.test.run.TestRunViewModel

class AgreementFragment : Fragment(), View.OnClickListener {

    private val viewModel: AgreementViewModel by viewModels()
    private lateinit var binding: FragmentAgreementBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgreementBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.snackBarShow.observe(viewLifecycleOwner, Observer { it ->
            if (it == true) {
                showSnackBar(view, viewModel.model.userAgreement)
                viewModel.showBarFinished()
            }
        })

        val onclick: View.OnClickListener

    }


    private fun showSnackBar(view: View, value: Boolean) {
        Snackbar.make(view, "agreement = ".plus(value.toString()), Snackbar.LENGTH_SHORT).show()

    }

    override fun onClick(v: View) {

    }

}