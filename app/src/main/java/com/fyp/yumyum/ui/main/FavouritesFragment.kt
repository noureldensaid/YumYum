package com.fyp.yumyum.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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

        viewModel.allFavorites.observe(viewLifecycleOwner, Observer {
            favAdapter.differ.submitList(it)
            Log.e("fav size", "onViewCreated: ${it.size}")
        })

        favAdapter = FavAdapter(viewModel)
        binding.favsRv.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = favAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


