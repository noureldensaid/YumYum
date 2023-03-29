package com.fyp.yumyum.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fyp.yumyum.R
import com.fyp.yumyum.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWelcomeBinding.bind(view)

        binding.getStartedBtn.setOnClickListener {
          findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}