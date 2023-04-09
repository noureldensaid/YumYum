package com.fyp.yumyum.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fyp.yumyum.R
import com.fyp.yumyum.databinding.FragmentLoginBinding
import com.fyp.yumyum.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        auth = Firebase.auth
        binding.apply {
            loginBtn.setOnClickListener {
                val email = editTextEmail.editText?.text.toString().trim()
                val psw = editTextPassword.editText?.text.toString().trim()

                if (email.isNotEmpty() && psw.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email, psw)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                binding.apply {
                                    loginBtn.visibility = View.INVISIBLE
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