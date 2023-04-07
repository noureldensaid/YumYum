package com.fyp.yumyum.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fyp.yumyum.R
import com.fyp.yumyum.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainNavController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()

    }


    private fun setUpNavigation() {
        mainNavController =
            (supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment).navController

        binding.bottomNavigationView.setupWithNavController(mainNavController)

        mainNavController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.apply {
                        bottomNavigationView.visibility = View.VISIBLE
                        toolbar.visibility = View.GONE
                    }
                }
                R.id.favouritesFragment -> {
                    binding.apply {
                        bottomNavigationView.visibility = View.VISIBLE
                        toolbar.visibility = View.GONE
                    }
                }
                R.id.searchFragment -> {
                    binding.apply {
                        bottomNavigationView.visibility = View.VISIBLE
                        toolbar.visibility = View.GONE
                    }
                }
                R.id.profileFragment -> {
                    binding.apply {
                        bottomNavigationView.visibility = View.VISIBLE
                        toolbar.visibility = View.GONE
                    }
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.GONE
                    binding.toolbar.visibility = View.VISIBLE
                }
            }
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        updateStatusBarColor("#F5F5F8")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.main_fragment_container)
        return navController.navigateUp()
    }

    private fun updateStatusBarColor(color: String) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor(color)
    }
}