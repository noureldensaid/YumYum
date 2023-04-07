package com.fyp.yumyum.ui.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.compose.ui.text.capitalize
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.fyp.yumyum.R
import com.fyp.yumyum.databinding.FragmentProfileBinding
import com.fyp.yumyum.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val viewModel: MainViewModel by viewModels<MainViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        binding.logoutBtn.setOnClickListener {
            logout()
        }
        showUserInfo()
    }

    private fun showUserInfo() {
        auth = Firebase.auth
        binding.userGmail.text = auth.currentUser?.email
        binding.userName.text = viewModel.getUserName()?.capitalize()
    }

    private fun logout() {
        val dialogBinding = layoutInflater.inflate(R.layout.layout_custom_dialog, null)
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnYes = dialogBinding.findViewById<Button>(R.id.btnYes).setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
        val btnNo = dialogBinding.findViewById<Button>(R.id.btnNo).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}

