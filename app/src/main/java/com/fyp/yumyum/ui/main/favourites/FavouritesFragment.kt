package com.fyp.yumyum.ui.main.favourites

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fyp.yumyum.R
import com.fyp.yumyum.adapters.FavAdapter
import com.fyp.yumyum.databinding.FragmentFavouritesBinding
import com.fyp.yumyum.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var favAdapter: FavAdapter
    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)

        favAdapter = FavAdapter(viewModel)

        binding.favsRv.apply {
             adapter = favAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        viewModel.allFavorites.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.favsRv.visibility = View.GONE
                binding.favEmptyList.visibility = View.VISIBLE
            } else {
                favAdapter.differ.submitList(it)
                Log.e("fav size", "onViewCreated: ${it.size}")
                binding.favEmptyList.visibility = View.GONE
                binding.favsRv.visibility = View.VISIBLE

            }
        })

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


