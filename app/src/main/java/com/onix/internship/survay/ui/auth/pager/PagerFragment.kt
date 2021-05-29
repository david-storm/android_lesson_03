package com.onix.internship.survay.ui.auth.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.onix.internship.survay.R
import com.onix.internship.survay.databinding.FragmentPagerBinding
import com.onix.internship.survay.ui.auth.login.LoginFragment
import com.onix.internship.survay.ui.auth.register.RegisterFragment


class PagerFragment : Fragment() {

    private lateinit var binding: FragmentPagerBinding
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
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.setText(
                when (position) {
                    0 -> R.string.login
                    else -> R.string.register
                }
            )
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }


    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar!!.show()
    }

}


class AuthAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LoginFragment()
            else -> RegisterFragment()
        }
    }
}