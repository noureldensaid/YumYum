package com.fyp.yumyum.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fyp.yumyum.ui.login.LoginActivity
import com.fyp.yumyum.R
import com.fyp.yumyum.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        auth = Firebase.auth

        binding.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }


    }
}