package com.onix.internship.survay.auth.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.onix.internship.survay.auth.login.LoginFragment
import com.onix.internship.survay.auth.register.RegisterFragment

class AuthAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LoginFragment()
            else -> RegisterFragment()
        }


    }


}