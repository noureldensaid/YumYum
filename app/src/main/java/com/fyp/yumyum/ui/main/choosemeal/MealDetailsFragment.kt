package com.fyp.yumyum.ui.main.choosemeal

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.fyp.yumyum.R
import com.fyp.yumyum.adapters.MealDetailsAdapter
import com.fyp.yumyum.databinding.FragmentMealDetailsBinding
import com.fyp.yumyum.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MealDetailsFragment : Fragment(R.layout.fragment_meal_details) {
    private val viewModel by viewModels<MainViewModel>()
    private var _binding: FragmentMealDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mealDetailsAdapter: MealDetailsAdapter
    private val args: MealDetailsFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMealDetailsBinding.bind(view)

        mealDetailsAdapter = MealDetailsAdapter()

        val mealId = args.mealId

        viewModel.getMealDetails(mealId)


        binding.mealDetail.apply {
            setHasFixedSize(true)
            adapter = mealDetailsAdapter
        }

        viewModel.mealDetails.observe(viewLifecycleOwner, Observer {
            mealDetailsAdapter.differ.submitList(it)
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}