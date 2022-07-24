package com.example.baseapp.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.baseapp.R
import com.example.baseapp.data.local.model.db.HeaderField
import com.example.baseapp.databinding.ListItemHeaderBinding

class PagedNewsAdapter(private val fm: FragmentManager) :
    PagingDataAdapter<HeaderField, PagedNewsAdapter.HeaderViewHolder>(
        HeaderDiffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HeaderViewHolder = HeaderViewHolder(
        ListItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        fm
    )

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class HeaderViewHolder(
        private val binding: ListItemHeaderBinding,
        private val fm: FragmentManager
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(input: HeaderField) {
            binding.txtTitle.text = input.webTitle
            binding.imageNews.load(
                uri = input.thumbnail,
            ) {
                placeholder(R.drawable.ic_launcher_background)
            }
            binding.txtDate.text = input.webPublicationDate
            itemView.setOnClickListener {
                fm.beginTransaction().replace(
                    R.id.container, DetailFragment().apply {
                        arguments = bundleOf("id" to input.id)
                    }
                ).addToBackStack("")
                    .commit()
            }
        }
    }
}

object HeaderDiffCallback : DiffUtil.ItemCallback<HeaderField>() {
    override fun areItemsTheSame(
        oldItem: HeaderField,
        newItem: HeaderField,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: HeaderField,
        newItem: HeaderField,
    ): Boolean = oldItem == newItem
}
