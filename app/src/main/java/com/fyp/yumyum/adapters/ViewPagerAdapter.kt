package com.fyp.yumyum.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fyp.yumyum.ui.login.LoginFragment
import com.fyp.yumyum.ui.login.SignUpFragment


class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        if (position == 0) LoginFragment() else SignUpFragment()

}
