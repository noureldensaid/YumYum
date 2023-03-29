package com.fyp.yumyum.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fyp.yumyum.ui.main.MainActivity
import com.fyp.yumyum.R
 import com.fyp.yumyum.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        auth = Firebase.auth
        binding.apply {
            signUpBtn.setOnClickListener {
                val email = editTextEmail.editText?.text.toString()
                val psw = editTextPassword.editText?.text.toString()

                auth.createUserWithEmailAndPassword(email, psw)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(activity, MainActivity::class.java))
                            activity?.finish()
                        } else {
                            Toast.makeText(context, task.exception!!.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}