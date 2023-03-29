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
    private val viewModel by viewModels<MainViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val imagesList = arrayListOf<SlideModel>()
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        setUpImageSlider(imagesList)
        binding.imageSlider.setImageList(imagesList, ScaleTypes.CENTER_CROP)

        homeAdapter = HomeAdapter()
        binding.homeRv.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = homeAdapter
        }

        viewModel.data.observe(viewLifecycleOwner, Observer { data ->
            Log.e("size ", data.size.toString());
            homeAdapter.differ.submitList(data)
        })

        homeAdapter.onItemClickListener = {
            val args = Bundle()
            args.putString("categoryName", it.strCategory)
            findNavController().navigate(R.id.action_homeFragment_to_mealsFragment, args)

        }


    }


    private fun setUpImageSlider(imagesList: ArrayList<SlideModel>) {
        imagesList.add(SlideModel("https://www.thespruceeats.com/thmb/PGh87G5Z6xe_wNfLYQsm7AuHo2U=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/unicorn-doughnuts-4773240-hero-01-9e468efed0f64d8a8fdc103f31cc4d8a.jpg"))
        imagesList.add(SlideModel("https://www.mashed.com/img/gallery/the-real-reason-your-grilled-steak-is-gray/l-intro-1618967456.jpg"))
        imagesList.add(SlideModel("https://i2.wp.com/www.downshiftology.com/wp-content/uploads/2022/09/Tuscan-Chicken-19.jpg"))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}