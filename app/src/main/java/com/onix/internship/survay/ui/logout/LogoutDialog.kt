package com.onix.internship.survay.ui.logout

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.onix.internship.survay.R
import com.onix.internship.survay.databinding.DialogLogoutBinding
import com.onix.internship.survay.ui.auth.pager.PagerFragment

class LogoutDialog : DialogFragment() {

    private lateinit var binding: DialogLogoutBinding

    private val viewModel: LogoutViewModel by viewModels { LogoutViewModelFactory() }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE);
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLogoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.dialogEvent.observe(viewLifecycleOwner, {
            if (it == true) {
                Log.i("test", "${findNavController().popBackStack()}")
                findNavController().navigate(LogoutDialogDirections.actionLogoutDialogToPagerFragment())
            } else if (it == false) {
                dismiss()
            }
        })
    }
}