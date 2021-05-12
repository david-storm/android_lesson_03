package com.onix.internship.survay.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs

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

        binding.textView.text = args.uid.toString()

        return binding.root
    }

}