package com.fyp.yumyum.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fyp.yumyum.R
import com.fyp.yumyum.adapters.MealAdapter
import com.fyp.yumyum.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val viewModel by viewModels<MainViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchAdapter: MealAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        searchAdapter = MealAdapter(viewModel)
        binding.searchRv.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = searchAdapter
        }
        binding.searchTv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.search(query)
                    binding.searchRv.scrollToPosition(0)
                    binding.searchTv.clearFocus()
                    return true
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        viewModel.searchData.observe(viewLifecycleOwner, Observer {
            searchAdapter.differ.submitList(it)
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}