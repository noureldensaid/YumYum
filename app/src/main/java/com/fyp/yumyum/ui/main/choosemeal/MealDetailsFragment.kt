package com.fyp.yumyum.ui.main.choosemeal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.fyp.yumyum.R
import com.fyp.yumyum.databinding.FragmentMealDetailsBinding
import com.fyp.yumyum.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MealDetailsFragment : Fragment(R.layout.fragment_meal_details) {
    private val viewModel: MainViewModel by viewModels<MainViewModel>()
    private var _binding: FragmentMealDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: MealDetailsFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMealDetailsBinding.bind(view)

        val mealId = args.mealId

        viewModel.getMealDetails(mealId)

        viewModel.mealDetails.observe(viewLifecycleOwner, Observer {
            binding.apply {
                if (it != null) {
                    updateToolbar(it.strMeal)
                    mealOrigin.text = "Origin: ${it.strArea}"
                    mealTags.text = it.strTags ?: "Miscellaneous"
                    mealRecipe.text = it.strInstructions
                    capacity1.text = it.strMeasure1
                    capacity2.text = it.strMeasure2
                    capacity3.text = it.strMeasure3
                    capacity4.text = it.strMeasure4
                    capacity5.text = it.strMeasure5
                    ingredient1.text = it.strIngredient1
                    ingredient2.text = it.strIngredient2
                    ingredient3.text = it.strIngredient3
                    ingredient4.text = it.strIngredient4
                    ingredient5.text = it.strIngredient5
                    Glide.with(view)
                        .load(it.strMealThumb)
                        .error(R.drawable.ic_wifi_broken)
                        .transform(CenterCrop(), RoundedCorners(32))
                        .placeholder(R.drawable.ic_default_placeholder)
                        .into(mealIv)
                }
            }
        })

        binding.fab.setOnClickListener {
            val mealUrl = viewModel.mealDetails.value?.strYoutube.toString()
            Log.e("mealURL", mealUrl.toString())
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mealUrl)))
        }


    }

    private fun updateToolbar(string: String?) {
        activity?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)?.title = string
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}