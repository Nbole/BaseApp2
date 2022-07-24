package com.example.baseapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.baseapp.R
import com.example.baseapp.databinding.FragmentBlankBinding
import com.example.baseapp.presentation.vm.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModel()
    private lateinit var binding: FragmentBlankBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PagedNewsAdapter(parentFragmentManager)
        binding.recycler.adapter = adapter
        binding.btnSearch.setOnClickListener {
            if (binding.editText.text.toString().isNotBlank()) {
                viewModel.setQuery(binding.editText.text.toString())
            }
        }

        adapter.addLoadStateListener {
            val emptyResult = (
                it.append != LoadState.Loading && it.append.endOfPaginationReached ||
                    it.refresh != LoadState.Loading && it.refresh.endOfPaginationReached
                ) && adapter.itemCount == 0

            binding.apply {
                swipe.isRefreshing =
                    it.refresh is LoadState.Loading || it.append is LoadState.Loading
                swipe.isVisible = !emptyResult
            }
        }

        binding.swipe.setOnRefreshListener {
            adapter.refresh()
        }

        viewModel.resultAmount.observe(viewLifecycleOwner) {
            binding.results.isVisible = it != null
            binding.results.text = getString(R.string.results_d, it)
        }

        viewModel.pagedNews.observe(viewLifecycleOwner) { list ->
            lifecycleScope.launch(Dispatchers.Default) {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    adapter.submitData(list)
                }
            }
        }
    }
}