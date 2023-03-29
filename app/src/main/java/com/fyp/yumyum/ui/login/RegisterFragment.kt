package com.fyp.yumyum.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.fyp.yumyum.R
import com.fyp.yumyum.adapters.ViewPagerAdapter
import com.fyp.yumyum.databinding.FragmentRegisterBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)
        setUpViewPager(childFragmentManager, lifecycle)

    }

    private fun setUpViewPager(fragmentManager: FragmentManager, lifecycle: Lifecycle) {
        binding.apply {
            val adapter = ViewPagerAdapter(fragmentManager, lifecycle)
            bookingsViewPager.adapter = adapter
            TabLayoutMediator(tabLayout, bookingsViewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Login"
                    1 -> tab.text = "Sign-up"
                }
            }.attach()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
