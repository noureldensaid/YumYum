package com.fyp.yumyum.ui.main.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fyp.yumyum.R
import com.fyp.yumyum.adapters.MealAdapter
import com.fyp.yumyum.databinding.FragmentSearchBinding
import com.fyp.yumyum.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels<MainViewModel>()
    private lateinit var searchAdapter: MealAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        viewModel.searchData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.isNotEmpty()) {
                    binding.searchPlaceholder.visibility = View.GONE
                    binding.searchRv.visibility = View.VISIBLE
                    binding.noResultsIv.visibility = View.GONE
                    searchAdapter.differ.submitList(it)
                } else {
                    binding.searchPlaceholder.visibility = View.VISIBLE
                }
            } else {
                binding.noResultsIv.visibility = View.VISIBLE
                binding.searchPlaceholder.visibility = View.GONE
                binding.searchRv.visibility = View.GONE

            }
        })
        searchAdapter = MealAdapter(viewModel)
        binding.searchRv.apply {
             layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = searchAdapter
        }
        binding.searchTv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.search(query)
                    binding.searchRv.scrollToPosition(0)
                    binding.searchTv.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchAdapter.onItemClickListener = {
            val args = Bundle()
            args.putString("mealId", it.idMeal)
            findNavController().navigate(R.id.action_searchFragment_to_mealDetailsFragment, args)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearSearchResults()
        binding.searchTv.setQuery("", false)
        binding.searchRv.visibility = View.GONE
        binding.searchPlaceholder.visibility = View.VISIBLE
        _binding = null
    }


}