package com.example.baseapp.presentation.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import coil.load
import com.example.baseapp.R
import com.example.baseapp.databinding.FragmentBlankBinding
import com.example.baseapp.databinding.FragmentNewsDetailBinding
import com.example.baseapp.presentation.vm.DetailFragmentViewModel
import com.example.baseapp.presentation.vm.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailBinding
    private val viewModel: DetailFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        binding.imgClose.setOnClickListener {
            activity?.onBackPressed()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = requireArguments()
        viewModel.setId(checkNotNull(args.getString("id")))
        viewModel.body.observe(viewLifecycleOwner) {
            binding.img.load(it.thumbnail)
            binding.txtBody.text = Html.fromHtml(it.body, Html.FROM_HTML_MODE_COMPACT)
            if (it.webPublicationDate.contains("T")) {
                binding.txtDate.text = it.webPublicationDate.split("T").firstOrNull()
            }
            binding.txtTitle.text = it.webTitle
        }
    }
}