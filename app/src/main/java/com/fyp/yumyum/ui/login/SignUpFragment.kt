package com.fyp.yumyum.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.fyp.yumyum.R
import com.fyp.yumyum.databinding.FragmentSignUpBinding
import com.fyp.yumyum.ui.main.MainActivity
import com.fyp.yumyum.ui.main.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val viewModel: MainViewModel by viewModels<MainViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        auth = Firebase.auth
        binding.apply {
            signUpBtn.setOnClickListener {
                val email = editTextEmail.editText?.text.toString().trim()
                val username = editTextUserName.editText?.text.toString().trim()
                val psw = editTextPassword.editText?.text.toString().trim()

                if (email.isNotEmpty() && psw.isNotEmpty() && username.isNotEmpty()) {
                    auth.createUserWithEmailAndPassword(email, psw)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                binding.apply {
                                    signUpBtn.visibility = View.GONE
                                    loadingProgressbar.visibility = View.VISIBLE
                                }
                                startActivity(Intent(activity, MainActivity::class.java))
                                activity?.finish()
                            } else {
                                Toast.makeText(
                                    context,
                                    task.exception!!.message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }

                    viewModel.saveUserName(username)

                } else {
                    Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}