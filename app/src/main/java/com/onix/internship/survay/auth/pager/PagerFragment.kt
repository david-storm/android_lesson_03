package com.onix.internship.survay.auth.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.onix.internship.survay.databinding.FragmentPagerBinding

class PagerFragment : Fragment() {

    private lateinit var binding : FragmentPagerBinding
    private lateinit var pagerAdapter: AuthAdapter
    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPagerBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pagerAdapter = AuthAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = pagerAdapter
        (activity as AppCompatActivity).supportActionBar!!.hide()
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = when (position){
                0 -> "Login"
                else -> "Register"
            }
        }.attach()
    }

}