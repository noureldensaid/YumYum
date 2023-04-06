package com.fyp.yumyum.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fyp.yumyum.R
import com.fyp.yumyum.adapters.FavAdapter
import com.fyp.yumyum.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()
    private var _binding: FragmentFavouritesBinding? = null
    private lateinit var favAdapter: FavAdapter
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)
        favAdapter = FavAdapter(viewModel)
        viewModel.allFavorites.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                favAdapter.differ.submitList(it)
                Log.e("fav size", "onViewCreated: ${it.size}")
                binding.favEmptyList.visibility = View.GONE
            } else {
                binding.favsRv.visibility = View.GONE
                binding.favEmptyList.visibility = View.VISIBLE
            }
        })

        binding.favsRv.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = favAdapter
        }

        favAdapter.onItemClickListener = {
            val args = Bundle()
            args.putString("mealId", it.idMeal)
            findNavController().navigate(
                R.id.action_favouritesFragment_to_mealDetailsFragment,
                args
            )

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}


