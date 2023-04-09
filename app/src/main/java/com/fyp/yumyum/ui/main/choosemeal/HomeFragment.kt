package com.fyp.yumyum.ui.main.choosemeal

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.fyp.yumyum.R
import com.fyp.yumyum.adapters.HomeAdapter
import com.fyp.yumyum.databinding.FragmentHomeBinding
import com.fyp.yumyum.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: MainViewModel by viewModels<MainViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        homeAdapter = HomeAdapter()

        homeAdapter.onItemClickListener = {
            val args = Bundle()
            args.putString("categoryName", it.strCategory)
            findNavController().navigate(R.id.action_homeFragment_to_mealsFragment, args)

        }

        binding.homeRv.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = homeAdapter
        }

        viewModel.data.observe(viewLifecycleOwner, Observer { data ->
            Log.e("size ", data.size.toString());
            homeAdapter.differ.submitList(data)
        })



        setUpImageSlider()

        updateWelcomeMsg()
    }

    private fun updateWelcomeMsg() {
        val username = viewModel.getUserName()?.capitalize()
        binding.userName.text = "Hello $username,"
    }

    private fun setUpImageSlider() {
        val imageUrls: List<SlideModel> = listOf(
            SlideModel("https://images.unsplash.com/photo-1504674900247-0877df9cc836?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"),
            SlideModel("https://images.unsplash.com/photo-1648913370516-4e3bf8f9aee8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1091&q=80"),
            SlideModel("https://images.unsplash.com/photo-1648912500384-94b96959c8b6?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80")
        )
        binding.imageSlider.setImageList(imageUrls, ScaleTypes.CENTER_CROP)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}